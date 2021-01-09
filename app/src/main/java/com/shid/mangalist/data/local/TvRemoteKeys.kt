package com.shid.mangalist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_anime_remote_keys")
data class TvRemoteKeys(
    @PrimaryKey
    val animeId: Long,
    val prevKey: Int?,
    val nextKey: Int?
) {
}