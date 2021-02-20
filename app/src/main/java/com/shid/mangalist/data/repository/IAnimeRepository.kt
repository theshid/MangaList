package com.shid.mangalist.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shid.mangalist.data.local.db.AnimeDatabase
import com.shid.mangalist.data.local.entities.*
import com.shid.mangalist.data.remote.AnimePagingSource
import com.shid.mangalist.data.remote.remoteMediators.*
import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.remote.response.detail.Promo
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class IAnimeRepository @Inject constructor(
    private val database: AnimeDatabase,
    private val airingAnimeRemoteMediator: AiringAnimeRemoteMediator,
    private val movieAnimeRemoteMediator: MovieAnimeRemoteMediator,
    private val ovaAnimeRemoteMediator: OvaAnimeRemoteMediator,
    private val tvAnimeRemoteMediator: TvAnimeRemoteMediator,
    private val upcomingAnimeRemoteMediator: UpcomingAnimeRemoteMediator
):AnimeRepository {
    override suspend fun getAiringAnime(): Flow<PagingData<AiringAnime>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = airingAnimeRemoteMediator,
            pagingSourceFactory = { database.animeDao().getAiringAnimes() }
        ).flow
    }

    override suspend fun getMovieAnime(): Flow<PagingData<MovieAnime>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = movieAnimeRemoteMediator,
            pagingSourceFactory = { database.animeDao().getMovies() }
        ).flow
    }

    override suspend fun getOvaAnime(): Flow<PagingData<OvaAnime>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = ovaAnimeRemoteMediator,
            pagingSourceFactory = { database.animeDao().getOvaAnimes() }
        ).flow
    }

    override suspend fun getTvAnime(): Flow<PagingData<TvAnime>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = tvAnimeRemoteMediator,
            pagingSourceFactory = { database.animeDao().getTvAnimes() }
        ).flow
    }

    override suspend fun getUpcomingAnime(): Flow<PagingData<UpcomingAnime>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = upcomingAnimeRemoteMediator,
            pagingSourceFactory = { database.animeDao().getUpcomingAnimes() }
        ).flow
    }



}