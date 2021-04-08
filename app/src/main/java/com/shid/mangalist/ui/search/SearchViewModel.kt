package com.shid.mangalist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.data.repository.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @ExperimentalPagingApi @Inject constructor(private val repository: IAnimeRepository) :
    ViewModel() {

    private var _animeResult = MutableLiveData<List<AnimeListResponse>>()
    val animeResult: LiveData<List<AnimeListResponse>>
        get() = _animeResult

    @ExperimentalPagingApi
    fun setResult(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.getSearchAnime(query)
                _animeResult.value = result
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}