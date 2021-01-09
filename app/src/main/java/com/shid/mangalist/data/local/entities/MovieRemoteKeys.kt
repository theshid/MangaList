package com.shid.mangalist.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_anime_remote_keys")
data class MovieRemoteKeys(
    @PrimaryKey
    val animeId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
