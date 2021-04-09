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
        private val title: TextView = itemView.findViewById(R.id.title)
        private val image: ImageView = itemView.findViewById(R.id.img_discover)
        fun bind(anime: BookmarkAnime) {
            score.text = anime.score.toString()
            title.text = anime.title
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