package com.shid.mangalist.data.local.entities

import androidx.room.Entity

@Entity(tableName = "tv_anime")
data class TvAnime(
    val id: Int? = 0,
    val title: String? = "",
    val imageUrl: String? = "",
    val type: String? = "",
    val episodes: Int? = 0,
    val score: Double? = 0.0
):AnimeType
