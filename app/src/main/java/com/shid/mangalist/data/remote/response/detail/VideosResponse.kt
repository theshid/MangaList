package com.shid.mangalist.data.remote.response.detail

import com.shid.mangalist.data.remote.response.detail.Promo
import com.squareup.moshi.Json

data class VideosResponse(
    @Json(name = "promo")
    val promo: List<Promo>
)