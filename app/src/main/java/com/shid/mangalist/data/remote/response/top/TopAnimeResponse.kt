package com.shid.mangalist.data.remote.response.top


import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.squareup.moshi.Json

data class TopAnimeResponse(
    @Json(name = "top")
    val top: List<AnimeListResponse>
)