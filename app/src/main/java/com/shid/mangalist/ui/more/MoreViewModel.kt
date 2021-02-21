package com.shid.mangalist.ui.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.shid.mangalist.data.local.entities.AiringAnime
import com.shid.mangalist.data.remote.AnimePagingSource
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.data.repository.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @ExperimentalPagingApi
@Inject constructor(private val animePagingSource: AnimePagingSource,
private val repository:IAnimeRepository) : ViewModel() {

    suspend fun getData(): Flow<PagingData<AnimeListResponse>>{
        return Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 20)
        ) {
            animePagingSource
        }.flow
            .cachedIn(viewModelScope)

    }

    @ExperimentalPagingApi
    suspend fun getTopAiringAnimes(): Flow<PagingData<AiringAnime>> {
        return repository
            .getAiringAnime()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }
}