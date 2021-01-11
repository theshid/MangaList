package com.shid.mangalist.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse

@Entity(tableName = "upcoming_anime")
 data class UpcomingAnime(
   @PrimaryKey
    val id: Int? = 0,
    val title: String? = "",
    val imageUrl: String? = "",
    val type: String? = "",
    val episodes: Int? = 0,
    val score: Double? = 0.0
):AnimeType
