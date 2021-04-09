package com.shid.mangalist.data.local.db

import androidx.room.*
import com.shid.mangalist.data.local.entities.BookmarkAnime

@Dao
interface AnimeDao {

    //Insert Bookmark Animes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmarkAnimes(anime: BookmarkAnime)

    //Get all bookmark animes
    @Query("SELECT * FROM bookmark_anime")
    fun getBookmarkAnimes():List<BookmarkAnime>

    /*//Search for Anime that is bookmarked
    @Query("SELECT * FROM bookmark_anime where anime_id IN (:animeId)")
    fun checkAnimeBookmark(animeId: Int):BookmarkAnime*/

    @Query("SELECT EXISTS (SELECT 1 FROM bookmark_anime WHERE anime_id IN (:id))")
    fun exists(id: Int): Int


    /*//Update favorite status of anime
    @Query("UPDATE bookmark_anime SET favorite = NOT favorite WHERE anime_id IN (:animeId)")
    fun updateFavorite(animeId: Int)*/

    //Delete bookmark anime
    @Delete
    fun unBookmarkAnime(anime: BookmarkAnime)


}