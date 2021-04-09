package com.shid.mangalist.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import kotlinx.parcelize.Parcelize

@Entity(tableName = "bookmark_anime")
@Parcelize
data class BookmarkAnime(
    @PrimaryKey
    @ColumnInfo(name = "anime_id") val id: Int? = 0,
    @ColumnInfo(name = "anime_title") val title: String? = "",
    @ColumnInfo(name = "image_url") val imageUrl: String? = "",
    @ColumnInfo(name = "anime_type") val type: String? = "",
    @ColumnInfo(name = "number_episode") val episodes: Int? = 0,
    @ColumnInfo(name = "anime_score") val score: Double? = 0.0,
    @ColumnInfo(name = "favorite") val favorite: Boolean? = false
): Parcelable {
    object ModelMapper {
        fun from(animeInfo: DetailAnimeResponse) =
            BookmarkAnime(animeInfo.id , animeInfo.title, animeInfo.imageUrl, animeInfo.type,
            animeInfo.episodes,animeInfo.score,true)
    }
}
