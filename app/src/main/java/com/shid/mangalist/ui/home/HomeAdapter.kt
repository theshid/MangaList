package com.shid.mangalist.ui.home

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shid.mangalist.R
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import me.turkergoksu.lib.PercentageView

class HomeAdapter(private val showDetail: (id: Int) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.AnimeViewHolder>() {

    private var listData = ArrayList<AnimeListResponse>()
    private val limit: Int = 10

    fun setData(newListData: List<AnimeListResponse>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeAdapter.AnimeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_anime_list, parent, false)

        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.AnimeViewHolder, position: Int) {
        val item = listData[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return if (listData.size > limit)
            limit
        else
            listData.size
    }

    inner class AnimeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val image: ImageView = itemView.findViewById(R.id.poster_image)
        private val rating:PercentageView = itemView.findViewById(R.id.percentageView)

        fun bind(animeListResponse: AnimeListResponse) {
            animeListResponse.score?.let { rating.setPercentage((it * 10).toInt()) }

            title.text = animeListResponse.title
            image.load(animeListResponse.imageUrl)

            itemView.apply {
                rootView.setOnClickListener {
                    animeListResponse.id?.let { it1 ->
                        showDetail(it1)
                    }
                }

            }
        }
    }
}