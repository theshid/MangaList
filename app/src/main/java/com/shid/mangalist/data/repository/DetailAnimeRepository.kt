package com.shid.mangalist.data.repository

import com.shid.mangalist.data.local.db.AnimeDatabase
import com.shid.mangalist.data.remote.RemoteDataSource
import com.shid.mangalist.data.remote.network.ApiServices
import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.remote.response.detail.Promo
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailAnimeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
):DetailRepository {

    companion object {
        @Volatile
        private var instance: DetailAnimeRepository? = null
        fun getInstance(remoteData: RemoteDataSource): DetailAnimeRepository =
            instance ?: synchronized(this) {
                instance ?: DetailAnimeRepository(remoteData)
            }
    }


    override suspend fun getDetailAnime(id: Int): DetailAnimeResponse {
        lateinit var animeDetail: DetailAnimeResponse
        remoteDataSource.getDetailAnime(id, object : RemoteDataSource.GetDetailCallback {
            override fun onAnimeReceived(anime: DetailAnimeResponse) {
                animeDetail = anime
            }
        })
        return animeDetail
    }

    override suspend fun getCharacters(id: Int): List<CharactersListResponse> {
        lateinit var charactersResult: List<CharactersListResponse>
        remoteDataSource.getCharacters(id, object : RemoteDataSource.GetCharactersCallback {
            override fun onCharactersReceived(characters: List<CharactersListResponse>) {
                charactersResult = characters
            }
        })
        return charactersResult
    }

    override suspend fun getVideos(id: Int): List<Promo> {
        lateinit var videosResult: List<Promo>
        remoteDataSource.getVideos(id, object : RemoteDataSource.GetVideosCallback {
            override fun onVideosReceived(videos: List<Promo>) {
                videosResult = videos
            }
        })
        return videosResult
    }
}