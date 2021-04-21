package com.shid.mangalist.ui.more


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.shid.mangalist.data.remote.AnimePagingSource
import com.shid.mangalist.data.remote.network.ApiServices
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.data.repository.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @ExperimentalPagingApi
@Inject constructor(private val repository: IAnimeRepository) : ViewModel() {

    private var _animeAiring = MutableLiveData<List<AnimeListResponse>>()
    val animeAiring: LiveData<List<AnimeListResponse>>
        get() = _animeAiring

   /* val animes: Flow<PagingData<AnimeListResponse>> = Pager(PagingConfig(pageSize = 20)) {
        AnimePagingSource(apiServices)
    }.flow
        .cachedIn(viewModelScope)*/

    @ExperimentalPagingApi
    fun setType(type: String) {
        viewModelScope.launch {
            try {
                val resultAiring = repository.getTopAnime(type)
                _animeAiring.value = resultAiring
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }


}