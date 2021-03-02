package com.shid.mangalist.ui.home

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shid.mangalist.R
import com.shid.mangalist.data.local.entities.AiringAnime
import com.shid.mangalist.data.local.entities.OvaAnime
import com.shid.mangalist.data.local.entities.UpcomingAnime
import com.skydoves.transformationlayout.TransformationLayout

class TopUpcomingAdapter constructor(private val delegate: AnimeDelegate):
    PagingDataAdapter<UpcomingAnime, TopUpcomingAdapter.UpcomingViewHolder>(TopUpcomingDiffUtils) {

    private var previousTime = SystemClock.elapsedRealtime()
    companion object {
        lateinit var dele: AnimeDelegate
        var prevTime:Long = 0
    }

    init {
        dele = delegate
        prevTime = previousTime
    }

    interface AnimeDelegate {
        fun onItemClick(upcomingAnime: UpcomingAnime, itemView: TransformationLayout)
    }
    override fun onBindViewHolder(holder: TopUpcomingAdapter.UpcomingViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopUpcomingAdapter.UpcomingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_anime_list, parent, false)
        return UpcomingViewHolder(view)
    }

    class UpcomingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val score: TextView = itemView.findViewById(R.id.score)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val image : ImageView = itemView.findViewById(R.id.poster_image)
        private val transformationLayout =
            itemView.findViewById<TransformationLayout>(R.id.transformationLayout)
        fun bindTo(anime: UpcomingAnime) {
            score.text = anime.score.toString()
            title.text = anime.title
            image.load(anime.imageUrl)

            transformationLayout.transitionName = anime.title
            itemView.apply {
                rootView.setOnClickListener(View.OnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - prevTime >= transformationLayout.duration) {
                        dele.onItemClick(anime, transformationLayout)
                        prevTime = now
                    }
                })
            }
        }
    }
}