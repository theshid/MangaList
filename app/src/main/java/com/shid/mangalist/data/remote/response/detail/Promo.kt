package com.shid.mangalist.data.remote.response.detail

import com.squareup.moshi.Json

data class Promo(
    @Json(name = "title")
    val title: String? = "",
    @Json(name = "image_url")
    val imageUrl: String? = "",
    @Json(name = "video_url")
    val videoUrl: String? = ""
)