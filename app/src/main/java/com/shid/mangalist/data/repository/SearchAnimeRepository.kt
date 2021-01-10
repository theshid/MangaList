package com.shid.mangalist.data.repository

import com.shid.mangalist.data.remote.RemoteDataSource
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchAnimeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
):SearchRepository{

    companion object {
        @Volatile
        private var instance: SearchAnimeRepository? = null
        fun getInstance(remoteData: RemoteDataSource): SearchAnimeRepository =
            instance ?: synchronized(this) {
                instance ?: SearchAnimeRepository(remoteData)
            }
    }
    override suspend fun getSearchAnime(query: String): List<AnimeListResponse> {
        lateinit var animeResult: List<AnimeListResponse>
        remoteDataSource.getSearchAnime(query, object : RemoteDataSource.GetAnimeCallback {
            override fun onAnimeReceived(animeList: List<AnimeListResponse>) {
                animeResult = animeList
            }
        })
        return animeResult
    }
}