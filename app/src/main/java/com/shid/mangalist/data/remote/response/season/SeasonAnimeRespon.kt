package com.shid.mangalist.data.remote.response.season


import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.squareup.moshi.Json

data class SeasonAnimeRespon(
    @Json(name = "season_name")
    val season: String,
    @Json(name = "season_year")
    val year: Int,
    @Json(name = "anime")
    val anime: List<AnimeListResponse>
)