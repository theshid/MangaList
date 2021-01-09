package com.shid.mangalist.data.remote.response.search


import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.squareup.moshi.Json

data class SearchAnimeResponse(
    @Json(name = "results")
    val results: List<AnimeListResponse>
)