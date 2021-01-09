package com.shid.mangalist.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airing_anime_remote_keys")
data class AiringRemoteKeys(
    @PrimaryKey
    val animeId: Int?,
    val prevKey: Int?,
    val nextKey: Int?
) :RemoteKeysType