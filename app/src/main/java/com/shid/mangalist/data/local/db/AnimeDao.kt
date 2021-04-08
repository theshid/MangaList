package com.shid.mangalist.data.local.db

import androidx.paging.PagingSource
import androidx.room.*
import com.shid.mangalist.data.local.entities.*

@Dao
interface AnimeDao {

    //Insert Bookmark Animes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkAnimes(anime:BookmarkAnime)

    //Get all bookmark animes
    @Query("SELECT * FROM bookmark_anime")
    fun getBookmarkAnimes():List<BookmarkAnime>

    //Delete bookmark anime
    @Delete
    fun unBookmarkAnime(anime:BookmarkAnime)


}