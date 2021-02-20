package com.shid.mangalist.data.remote.remoteMediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shid.mangalist.data.local.db.AnimeDatabase
import com.shid.mangalist.data.local.entities.AiringAnime
import com.shid.mangalist.data.local.entities.AiringRemoteKeys
import com.shid.mangalist.data.local.entities.MovieAnime
import com.shid.mangalist.data.local.entities.MovieRemoteKeys
import com.shid.mangalist.data.remote.network.ApiServices
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.utils.Constants
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieAnimeRemoteMediator @Inject constructor(
    private val services: ApiServices,
    private val database: AnimeDatabase
): RemoteMediator<Int, MovieAnime>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieAnime>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val nextKey = remoteKeys.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                prevKey
            }
        }

        try {
            val animeResponse = services.getTop(Constants.MOVIE_ANIME)

            //val data = movieResponse.mapDataToPopularMovies()


            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.animeRemoteKeysDao().clearMoviesRemoteKeys()
                    database.animeDao().clearMovies()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey =  page + 1
                val keys = animeResponse.top.map {
                    MovieRemoteKeys(
                        animeId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                database.animeRemoteKeysDao().insertAllMoviesKeys(keys)
                database.animeDao().insertMoviesAnime(animeResponse.top.map {
                    MovieAnime(
                        id = it.id,
                        title = it.title,
                        imageUrl = it.imageUrl,
                        type = it.type,
                        episodes = it.episodes,
                        score = it.score
                    )
                })
            }
            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieAnime>): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.animeRemoteKeysDao().remoteKeysByMovieId(repo.id)

        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieAnime>): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { anime ->
            database.animeRemoteKeysDao().remoteKeysByMovieId(anime.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieAnime>): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.animeRemoteKeysDao().remoteKeysByMovieId(id)
            }
        }
    }



}