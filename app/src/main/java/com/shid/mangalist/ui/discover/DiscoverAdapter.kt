package com.shid.mangalist.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shid.mangalist.R
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.ui.home.HomeAdapter
import me.turkergoksu.lib.PercentageView

class DiscoverAdapter (private val showDetail: (id: Int) -> Unit) :
    RecyclerView.Adapter<DiscoverAdapter.DiscoverAnimeViewHolder>() {

    private var listData = ArrayList<AnimeListResponse>()


    fun setData(newListData: List<AnimeListResponse>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscoverAnimeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_anime_discover, parent, false)
        return DiscoverAnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiscoverAnimeViewHolder, position: Int) {
        val item = listData[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listData.size


    inner class DiscoverAnimeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val txt_score: TextView = itemView.findViewById(R.id.txt_score)
        private val score: TextView = itemView.findViewById(R.id.score)
        private val rating: PercentageView = itemView.findViewById(R.id.percentageView)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val image: ImageView = itemView.findViewById(R.id.img_discover)
        fun bind(animeListResponse: AnimeListResponse) {
            animeListResponse.score?.let { rating.setPercentage((it * 10).toInt()) }
            if (animeListResponse.score.toString() == "null"){
                txt_score.visibility = View.VISIBLE
                score.visibility = View.VISIBLE
                score.text = "N/A"
                rating.visibility = View.GONE
            }

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