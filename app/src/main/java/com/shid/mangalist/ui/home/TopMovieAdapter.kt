package com.shid.mangalist.ui.home

import android.graphics.Movie
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
import com.shid.mangalist.data.local.entities.MovieAnime
import com.shid.mangalist.data.local.entities.TvAnime
import com.skydoves.transformationlayout.TransformationLayout

class TopMovieAdapter constructor(private val delegate: AnimeDelegate) :
    PagingDataAdapter<MovieAnime, TopMovieAdapter.MovieViewHolder>(TopMovieDiffUtils) {

    var previousTime = SystemClock.elapsedRealtime()
    companion object {
        lateinit var dele: AnimeDelegate
        var prevTime:Long = 0
    }

    init {
        dele = delegate
        prevTime = previousTime
    }
    override fun onBindViewHolder(holder: TopMovieAdapter.MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }

    interface AnimeDelegate {
        fun onItemClick(movieAnime: MovieAnime,itemView: TransformationLayout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopMovieAdapter.MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_anime_list, parent, false)
        return MovieViewHolder(view)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val score: TextView = itemView.findViewById(R.id.score)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val image : ImageView = itemView.findViewById(R.id.poster_image)
        private val transformationLayout =
            itemView.findViewById<TransformationLayout>(R.id.transformationLayout)
        fun bindTo(anime: MovieAnime) {
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