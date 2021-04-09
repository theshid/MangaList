package com.shid.mangalist.data.remote.network

import com.shid.mangalist.data.remote.response.detail.CharactersResponse
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.remote.response.detail.VideosResponse
import com.shid.mangalist.data.remote.response.search.SearchAnimeResponse
import com.shid.mangalist.data.remote.response.season.SeasonAnimeRespon
import com.shid.mangalist.data.remote.response.top.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    //top/anime/{page}/{type}
    @GET("top/anime/1/{type}")
    suspend fun getTopAnime(@Path("type") type: String): TopAnimeResponse

    @GET("top/anime/{page}/{type}")
    suspend fun getTopAnimePaging(@Path("type") type: String,@Path("page") page:Int): TopAnimeResponse

    @GET("top/anime/1/{type}")
    suspend fun getTop(@Path("type") type: String): TopAnimeResponse

    @GET("anime/{mal_id}")
    suspend fun getDetailAnime(@Path("mal_id") mal_id: Int): DetailAnimeResponse

    @GET("season/{year}/{season}")
    suspend fun getSeasonAnime(
        @Path("year") seasonYear: Int,
        @Path("season") seasonName: String
    ): SeasonAnimeRespon

    @GET("search/anime?&page=1")
    suspend fun getSearchAnime(@Query("q") query: String): SearchAnimeResponse

    @GET("anime/{id}/characters_staff")
    suspend fun getCharacters(@Path("id") id: Int): CharactersResponse

    @GET("anime/{id}/videos")
    suspend fun getVideos(@Path("id") id: Int): VideosResponse
}