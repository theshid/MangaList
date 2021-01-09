package com.shid.mangalist.data.local

import androidx.room.Entity

@Entity(tableName = "ova_anime")
data class OvaAnime(
    val id: Int? = 0,
    val title: String? = "",
    val imageUrl: String? = "",
    val type: String? = "",
    val episodes: Int? = 0,
    val score: Double? = 0.0
)
