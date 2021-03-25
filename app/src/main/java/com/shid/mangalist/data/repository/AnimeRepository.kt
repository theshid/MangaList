package com.shid.mangalist.data.repository

import androidx.paging.PagingData
import com.shid.mangalist.data.local.entities.*
import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.remote.response.detail.Promo
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    suspend fun getTopAnime(type:String): List<AnimeListResponse>

    suspend fun getDetailAnime(id: Int): DetailAnimeResponse

    suspend fun getSeasonAnime(year: Int, season: String): List<AnimeListResponse>

    suspend fun getSearchAnime(query: String): List<AnimeListResponse>

    suspend fun getCharacters(id: Int): List<CharactersListResponse>

    suspend fun getVideos(id: Int): List<Promo>
}





    //suspend fun getSeasonAnime(year: Int, season: String): List<AnimeListResponse>




