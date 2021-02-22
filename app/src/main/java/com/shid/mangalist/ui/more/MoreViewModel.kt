package com.shid.mangalist.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.shid.mangalist.data.local.entities.*
import com.shid.mangalist.data.remote.AnimePagingSource
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.data.repository.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @ExperimentalPagingApi
@Inject constructor(private val repository: IAnimeRepository) : ViewModel() {
    @ExperimentalPagingApi

    suspend fun getTopAiringAnimes(): Flow<PagingData<AiringAnime>> {
        return repository
            .getAiringAnime()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }

    @ExperimentalPagingApi
    suspend fun getData(type:String){
        when (type) {
            "airing" -> getTopAiringAnimes()
            "upcoming" -> getTopUpcomingAnimes()
            "movie" -> getTopMovieAnimes()
            "tv" -> getTopTvAnimes()
            "ova" -> getTopOvaAnimes()
            else -> { // Note the block
                return
            }
        }
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

    @ExperimentalPagingApi

    suspend fun getTopOvaAnimes(): Flow<PagingData<OvaAnime>> {
        return repository
            .getOvaAnime()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }

}