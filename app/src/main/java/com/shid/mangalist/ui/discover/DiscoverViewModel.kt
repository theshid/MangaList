package com.shid.mangalist.ui.discover

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

@ExperimentalPagingApi
@HiltViewModel
class DiscoverViewModel @Inject constructor( private val repository: IAnimeRepository) : ViewModel() {

    private var _animeSeason = MutableLiveData<List<AnimeListResponse>>()
    val animeSeason: LiveData<List<AnimeListResponse>>
        get() = _animeSeason

    fun setSeason(year: Int, season: String) {
        viewModelScope.launch {
            try {
                val resultAiring = repository.getSeasonAnime(year, season)
                _animeSeason.value = resultAiring
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}