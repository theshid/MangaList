package com.shid.mangalist.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shid.mangalist.data.local.entities.*

@Database(
    entities = [AiringAnime::class, AiringRemoteKeys::class,
        MovieAnime::class, MovieRemoteKeys::class,
        OvaAnime::class, OvaRemoteKeys::class,
        TvAnime::class, TvRemoteKeys::class,
        UpcomingAnime::class, UpcomingRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
    abstract fun animeRemoteKeysDao(): AnimeRemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: AnimeDatabase? = null

        fun getInstance(context: Context): AnimeDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AnimeDatabase::class.java, "MovieDatabase.db"
            )
                .build()
    }
}