package com.shid.mangalist.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "airing_anime")
@Parcelize
data class AiringAnime(
    @PrimaryKey
    val id: Int? = 0,
    val title: String? = "",
    val imageUrl: String? = "",
    val type: String? = "",
    val episodes: Int? = 0,
    val score: Double? = 0.0
):AnimeType, Parcelable
