package com.shid.mangalist.data.remote.response.detail

import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.squareup.moshi.Json

data class CharactersResponse(
    @Json(name = "characters")
    val characters: List<CharactersListResponse>
)