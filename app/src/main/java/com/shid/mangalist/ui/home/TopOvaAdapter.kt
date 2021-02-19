package com.shid.mangalist.ui.home

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
import com.shid.mangalist.data.local.entities.OvaAnime

class TopOvaAdapter : PagingDataAdapter<OvaAnime, TopOvaAdapter.OvaViewHolder>(TopOvaDiffUtils) {


    override fun onBindViewHolder(holder: TopOvaAdapter.OvaViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopOvaAdapter.OvaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_anime_list, parent, false)
        return OvaViewHolder(view)
    }

    class OvaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val score: TextView = itemView.findViewById(R.id.score)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val image : ImageView = itemView.findViewById(R.id.poster_image)
        fun bindTo(anime: OvaAnime) {
            score.text = anime.score.toString()
            title.text = anime.title
            image.load(anime.imageUrl)
        }
    }
}