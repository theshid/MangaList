package com.shid.mangalist.data.repository

import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse

interface SearchRepository {
    suspend fun getSearchAnime(query: String): List<AnimeListResponse>
}