package com.shid.mangalist.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.shid.mangalist.data.local.entities.AiringAnime
import com.shid.mangalist.data.local.entities.MovieAnime
import com.shid.mangalist.data.local.entities.TvAnime
import com.shid.mangalist.data.local.entities.UpcomingAnime

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