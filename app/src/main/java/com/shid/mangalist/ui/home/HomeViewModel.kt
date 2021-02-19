package com.shid.mangalist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shid.mangalist.data.local.entities.AiringAnime
import com.shid.mangalist.data.local.entities.MovieAnime
import com.shid.mangalist.data.local.entities.TvAnime
import com.shid.mangalist.data.local.entities.UpcomingAnime
import com.shid.mangalist.data.repository.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @ExperimentalPagingApi
 @Inject constructor(
    private val repository: IAnimeRepository
): ViewModel() {

    @ExperimentalPagingApi

    suspend fun getTopAiringAnimes(): Flow<PagingData<AiringAnime>> {
        return repository
            .getAiringAnime()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }

    @ExperimentalPagingApi

    suspend fun getTopUpcomingAnimes(): Flow<PagingData<UpcomingAnime>> {
        return repository
            .getUpcomingAnime()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }

    @ExperimentalPagingApi

    suspend fun getTopTvAnimes(): Flow<PagingData<TvAnime>> {
        return repository
            .getTvAnime()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }

    @ExperimentalPagingApi

    suspend fun getTopMovieAnimes(): Flow<PagingData<MovieAnime>> {
        return repository
            .getMovieAnime()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }
}