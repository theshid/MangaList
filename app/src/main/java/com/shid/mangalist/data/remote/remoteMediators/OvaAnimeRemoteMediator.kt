package com.shid.mangalist.data.remote.remoteMediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shid.mangalist.data.local.db.AnimeDatabase
import com.shid.mangalist.data.local.entities.OvaAnime
import com.shid.mangalist.data.local.entities.OvaRemoteKeys
import com.shid.mangalist.data.remote.network.ApiServices
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.utils.Constants
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class OvaAnimeRemoteMediator @Inject constructor(
    private val services: ApiServices,
    private val database: AnimeDatabase
) : RemoteMediator<Int, AnimeListResponse>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeListResponse>
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
            val animeResponse = services.getTopAnime(Constants.OVA_ANIME, page)

            //val data = movieResponse.mapDataToPopularMovies()


            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.animeRemoteKeysDao().clearOvaAnimeRemoteKeys()
                    database.animeDao().clearOvaAnimes()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = page + 1
                val keys = animeResponse.top.map {
                    OvaRemoteKeys(
                        animeId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                database.animeRemoteKeysDao().insertAllOvaRemoteKeys(keys)
                database.animeDao().insertOvaAnimes(animeResponse.top.map {
                    OvaAnime(
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

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimeListResponse>): OvaRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.animeRemoteKeysDao().remoteKeysByOvaAnimeId(repo.id)

        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AnimeListResponse>): OvaRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { anime ->
            database.animeRemoteKeysDao().remoteKeysByOvaAnimeId(anime.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AnimeListResponse>): OvaRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.animeRemoteKeysDao().remoteKeysByOvaAnimeId(id)
            }
        }
    }


}