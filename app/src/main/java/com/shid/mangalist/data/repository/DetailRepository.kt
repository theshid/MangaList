package com.shid.mangalist.data.repository

import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.remote.response.detail.Promo

interface DetailRepository {

    suspend fun getDetailAnime(id: Int): DetailAnimeResponse

    suspend fun getCharacters(id: Int): List<CharactersListResponse>

    suspend fun getVideos(id: Int): List<Promo>
}