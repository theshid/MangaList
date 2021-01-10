package com.shid.mangalist.data.repository

import androidx.paging.PagingData
import com.shid.mangalist.data.local.entities.*
import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.remote.response.detail.Promo
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    suspend fun getAiringAnime(): Flow<PagingData<AiringAnime>>

    suspend fun getMovieAnime(): Flow<PagingData<MovieAnime>>

    suspend fun getOvaAnime(): Flow<PagingData<OvaAnime>>

    suspend fun getTvAnime(): Flow<PagingData<TvAnime>>

    suspend fun getUpcomingAnime(): Flow<PagingData<UpcomingAnime>>





    //suspend fun getSeasonAnime(year: Int, season: String): List<AnimeListResponse>




}