package com.shid.mangalist.data.remote.response.detail

import com.squareup.moshi.Json

data class Genres(
    @Json(name = "mal_id")
    val id: Int? = 0,
    @Json(name = "name")
    val name: String? = ""
)