package com.shid.mangalist.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.shid.mangalist.data.local.db.AnimeDatabase
import com.shid.mangalist.data.local.entities.BookmarkAnime
import com.shid.mangalist.data.remote.response.detail.Promo
import com.shid.mangalist.data.repository.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class BookmarksViewModel @Inject constructor(private val database: AnimeDatabase) : ViewModel() {
    private var _list = MutableLiveData<List<BookmarkAnime>>()
    val bookmarkList: LiveData<List<BookmarkAnime>>
        get() = _list

    fun getAllBookmarks(){
        viewModelScope.launch(Dispatchers.IO){
            val animeList = database.animeDao().getBookmarkAnimes()
            _list.postValue(animeList)
        }
    }
}