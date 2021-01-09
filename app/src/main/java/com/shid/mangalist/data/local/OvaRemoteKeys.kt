package com.shid.mangalist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ova_anime_remote_keys")
data class OvaRemoteKeys(
    @PrimaryKey
    val animeId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
