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
        private val score: TextView = itemView.findViewById(R.id.score)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val image: ImageView = itemView.findViewById(R.id.img_discover)
        fun bind(animeListResponse: AnimeListResponse) {
            if (animeListResponse.score.toString() == "null"){
                score.text = "N/A"
            } else{
                score.text = animeListResponse.score.toString()
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