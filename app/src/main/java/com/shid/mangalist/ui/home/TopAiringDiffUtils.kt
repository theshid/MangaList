package com.shid.mangalist.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.shid.mangalist.data.local.entities.*
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse

object TopAiringDiffUtils : DiffUtil.ItemCallback<AiringAnime>() {


    override fun areItemsTheSame(
        oldItem: AiringAnime,
        newItem: AiringAnime
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: AiringAnime,
        newItem: AiringAnime
    ): Boolean = oldItem.id == newItem.id

}


object TopUpcomingDiffUtils : DiffUtil.ItemCallback<UpcomingAnime>() {


    override fun areItemsTheSame(
        oldItem: UpcomingAnime,
        newItem: UpcomingAnime
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: UpcomingAnime,
        newItem: UpcomingAnime
    ): Boolean = oldItem.id == newItem.id

}

object TopTvDiffUtils : DiffUtil.ItemCallback<TvAnime>() {


    override fun areItemsTheSame(
        oldItem: TvAnime,
        newItem: TvAnime
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: TvAnime,
        newItem: TvAnime
    ): Boolean = oldItem.id == newItem.id

}

object TopMovieDiffUtils : DiffUtil.ItemCallback<MovieAnime>() {


    override fun areItemsTheSame(
        oldItem: MovieAnime,
        newItem: MovieAnime
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: MovieAnime,
        newItem: MovieAnime
    ): Boolean = oldItem.id == newItem.id

}

object TopOvaDiffUtils : DiffUtil.ItemCallback<OvaAnime>() {


    override fun areItemsTheSame(
        oldItem: OvaAnime,
        newItem: OvaAnime
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: OvaAnime,
        newItem: OvaAnime
    ): Boolean = oldItem.id == newItem.id

}

object MoreDiffUtils : DiffUtil.ItemCallback<AnimeListResponse>() {


    override fun areItemsTheSame(
        oldItem: AnimeListResponse,
        newItem: AnimeListResponse
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: AnimeListResponse,
        newItem: AnimeListResponse
    ): Boolean = oldItem.id == newItem.id

}