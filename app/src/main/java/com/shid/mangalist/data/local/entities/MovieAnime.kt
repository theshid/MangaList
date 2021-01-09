package com.shid.mangalist.data.local.entities

import androidx.room.Entity

@Entity(tableName = "movie_anime")
data class MovieAnime(
    val id: Int? = 0,
    val title: String? = "",
    val imageUrl: String? = "",
    val type: String? = "",
    val episodes: Int? = 0,
    val score: Double? = 0.0
):AnimeType
