package com.shid.mangalist.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_anime_remote_keys")
data class UpcomingRemoteKeys(
    @PrimaryKey
    val animeId: Int?,
    val prevKey: Int?,
    val nextKey: Int?
):RemoteKeysType
