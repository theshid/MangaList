package com.shid.mangalist.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shid.mangalist.R
import com.shid.mangalist.data.local.entities.BookmarkAnime
import me.turkergoksu.lib.PercentageView

class BookmarkAdapter(private val showDetail: (id: Int) -> Unit) :
    RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    private var listData = ArrayList<BookmarkAnime>()


    fun setData(newListData: List<BookmarkAnime>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarkViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_anime_discover, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val item = listData[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return if (listData.size == 0) {
            0
        } else listData.size
    }


    inner class BookmarkViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val score: TextView = itemView.findViewById(R.id.score)
        private val txt_score: TextView = itemView.findViewById(R.id.txt_score)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val rating: PercentageView = itemView.findViewById(R.id.percentageView)
        private val image: ImageView = itemView.findViewById(R.id.img_discover)
        fun bind(anime: BookmarkAnime) {
            anime.score?.let { rating.setPercentage((it * 10).toInt()) }
            title.text = anime.title
            if (anime.score.toString() == "null"){
                txt_score.visibility = View.VISIBLE
                score.visibility = View.VISIBLE
                score.text = "N/A"
                rating.visibility = View.GONE
            }
            image.load(anime.imageUrl)

            itemView.apply {
                rootView.setOnClickListener {
                    anime.id?.let { it1 ->
                        showDetail(it1)
                    }
                }

            }
        }
    }
}