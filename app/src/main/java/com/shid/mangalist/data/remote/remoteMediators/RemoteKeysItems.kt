package com.shid.mangalist.data.remote.remoteMediators

import androidx.paging.PagingState
import com.shid.mangalist.data.local.entities.AiringRemoteKeys
import com.shid.mangalist.data.local.entities.RemoteKeysType
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse

interface RemoteKeysItems {
    suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimeListResponse>): RemoteKeysType?
    suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AnimeListResponse>):RemoteKeysType?
    suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AnimeListResponse>):RemoteKeysType?
}