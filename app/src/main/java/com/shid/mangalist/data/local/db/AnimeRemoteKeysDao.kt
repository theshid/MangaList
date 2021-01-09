package com.shid.mangalist.data.local.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shid.mangalist.data.local.entities.*

interface AnimeRemoteKeysDao {

    //Airing Animes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAiringKeys(remoteKey: List<AiringRemoteKeys>)

    @Query("SELECT * FROM airing_anime_remote_keys WHERE animeId = :mangaId")
    suspend fun remoteKeysByAiringAnimeId(mangaId: Long): AiringRemoteKeys?

    @Query("DELETE FROM airing_anime_remote_keys")
    suspend fun clearAiringAnimeRemoteKeys()

    //Movie Anime
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMoviesKeys(remoteKey: List<MovieRemoteKeys>)

    @Query("SELECT * FROM movie_anime_remote_keys WHERE animeId = :mangaId")
    suspend fun remoteKeysByMovieId(mangaId: Long): MovieRemoteKeys?

    @Query("DELETE FROM movie_anime_remote_keys")
    suspend fun clearMoviesRemoteKeys()

    //Ova Anime
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOvaRemoteKeys(remoteKey: List<OvaRemoteKeys>)

    @Query("SELECT * FROM ova_anime_remote_keys WHERE animeId = :mangaId")
    suspend fun remoteKeysByOvaAnimeId(mangaId: Long): OvaRemoteKeys?

    @Query("DELETE FROM ova_anime_remote_keys")
    suspend fun clearOvaAnimeRemoteKeys()

    //Tv Anime
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTvAnimesKeys(remoteKey: List<TvRemoteKeys>)

    @Query("SELECT * FROM tv_anime_remote_keys WHERE animeId = :mangaId")
    suspend fun remoteKeysByTvAnimeId(mangaId: Long): TvRemoteKeys?

    @Query("DELETE FROM tv_anime_remote_keys")
    suspend fun clearTvAnimeRemoteKeys()

    //Upcoming Animes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUpcomingAnimesKeys(remoteKey: List<UpcomingRemoteKeys>)

    @Query("SELECT * FROM upcoming_anime_remote_keys WHERE animeId = :mangaId")
    suspend fun remoteKeysByUpcomingAnimeId(mangaId: Long): UpcomingRemoteKeys?

    @Query("DELETE FROM upcoming_anime_remote_keys")
    suspend fun clearRemoteKeys()

}