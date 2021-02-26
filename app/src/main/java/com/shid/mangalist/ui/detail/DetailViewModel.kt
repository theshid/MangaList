package com.shid.mangalist.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.remote.response.detail.Promo
import com.shid.mangalist.data.repository.DetailAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailAnimeRepository) : ViewModel() {
    private var _anime = MutableLiveData<DetailAnimeResponse>()
    val anime: LiveData<DetailAnimeResponse>
        get() = _anime

    private var _characters = MutableLiveData<List<CharactersListResponse>>()
    val characters: LiveData<List<CharactersListResponse>>
        get() = _characters

    private var _videos = MutableLiveData<List<Promo>>()
    val videos: LiveData<List<Promo>>
        get() = _videos

    fun setDetailAnime(id: Int) {
        viewModelScope.launch {
            val detailAnime = repository.getDetailAnime(id)
            _anime.value = detailAnime

            val characters = repository.getCharacters(id)
            _characters.value = characters

            val videos = repository.getVideos(id)
            _videos.value = videos
        }
    }
}