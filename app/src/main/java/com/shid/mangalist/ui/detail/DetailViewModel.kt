package com.shid.mangalist.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shid.mangalist.data.local.db.AnimeDatabase
import com.shid.mangalist.data.local.entities.BookmarkAnime
import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.remote.response.detail.Promo
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.data.repository.DetailAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailAnimeRepository,
    private val database: AnimeDatabase
) : ViewModel() {
    private var _anime = MutableLiveData<DetailAnimeResponse>()
    val anime: LiveData<DetailAnimeResponse>
        get() = _anime

    private var _isAnimeInDb = MutableLiveData<Int>()
    val isAnimeInDb: LiveData<Int>
        get() = _isAnimeInDb

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

    fun checkIfAnimeIsFavorite(animeId: Int) {

        viewModelScope.launch(Dispatchers.IO){
            val checkAnime = database.animeDao().exists(animeId)
            Log.d("Detail", "value of anime check:$checkAnime")

                _isAnimeInDb.postValue(database.animeDao().exists(animeId))


            Log.d("Detail", "value of anime:${isAnimeInDb.value}")
        }


    }

    fun setFavorite(anime: DetailAnimeResponse) {
        val bookmarkAnime = BookmarkAnime.ModelMapper.from(anime)
        viewModelScope.launch(Dispatchers.IO){
            database.animeDao().insertBookmarkAnimes(bookmarkAnime)
        }

    }

    fun unSetFavorite(anime: DetailAnimeResponse) {
        val bookmarkAnime = BookmarkAnime.ModelMapper.from(anime)
        viewModelScope.launch(Dispatchers.IO){
            database.animeDao().unBookmarkAnime(bookmarkAnime)
        }

    }
}