package com.shid.mangalist.data.remote.remoteMediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shid.mangalist.data.local.db.AnimeDatabase
import com.shid.mangalist.data.local.entities.TvAnime
import com.shid.mangalist.data.local.entities.TvRemoteKeys
import com.shid.mangalist.data.remote.network.ApiServices
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.utils.Constants
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TvAnimeRemoteMediator @Inject constructor(
    private val services: ApiServices,
    private val database: AnimeDatabase
) : RemoteMediator<Int, TvAnime>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvAnime>
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
            val animeResponse = services.getTop(Constants.TV_ANIME)

            //val data = movieResponse.mapDataToPopularMovies()


            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.animeRemoteKeysDao().clearTvAnimeRemoteKeys()
                    database.animeDao().clearTvAnimes()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = page + 1
                val keys = animeResponse.top.map {
                    TvRemoteKeys(
                        animeId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                database.animeRemoteKeysDao().insertAllTvAnimesKeys(keys)
                database.animeDao().insertTvAnimes(animeResponse.top.map {
                    TvAnime(
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

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TvAnime>): TvRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.animeRemoteKeysDao().remoteKeysByTvAnimeId(repo.id)

        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TvAnime>): TvRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { anime ->
            database.animeRemoteKeysDao().remoteKeysByTvAnimeId(anime.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TvAnime>): TvRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.animeRemoteKeysDao().remoteKeysByTvAnimeId(id)
            }
        }
    }


}