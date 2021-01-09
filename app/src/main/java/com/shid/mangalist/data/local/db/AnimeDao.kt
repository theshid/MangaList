package com.shid.mangalist.data.local.db

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shid.mangalist.data.local.entities.*

interface AnimeDao {

    //Airing Anime
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiringAnimes(movieResponse: List<AiringAnime>)

    @Query("select * from airing_anime")
    fun getAiringAnimes(): PagingSource<Int, AiringAnime>

    @Query("DELETE FROM airing_anime")
    suspend fun clearAiringAnimes()

    //Movie Anime
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesAnime(movieResponse: List<MovieAnime>)

    @Query("select * from movie_anime")
    fun getMovies(): PagingSource<Int, MovieAnime>

    @Query("DELETE FROM movie_anime")
    suspend fun clearMovies()

    //Ova Anime
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOvaAnimes(movieResponse: List<OvaAnime>)

    @Query("select * from ova_anime")
    fun getOvaAnimes(): PagingSource<Int, OvaAnime>

    @Query("DELETE FROM ova_anime")
    suspend fun clearOvaAnimes()


    //Tv Animes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvAnimes(movieResponse: List<TvAnime>)

    @Query("select * from tv_anime")
    fun getTvAnimes(): PagingSource<Int, TvAnime>

    @Query("DELETE FROM tv_anime")
    suspend fun clearTvAnimes()

    //Upcoming Animes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieResponse: List<UpcomingAnime>)

    @Query("select * from upcoming_anime")
    fun getUpcomingAnimes(): PagingSource<Int, UpcomingAnime>

    @Query("DELETE FROM upcoming_anime")
    suspend fun clearUpcomingAnimes()
}