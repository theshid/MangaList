package com.shid.mangalist.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shid.mangalist.R
import com.shid.mangalist.data.remote.response.detail.Promo

class VideoAdapter(private val showVideo: (url: String?) -> Unit) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var listData = ArrayList<Promo>()

    fun setData(newListData: List<Promo>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(

            R.layout.item_videos_list,
            parent,
            false
        )
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class VideoViewHolder(private val itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val image_video: ImageView = itemView.findViewById(R.id.image_video)
        fun bind(mPromo: Promo) {

            image_video.load(mPromo.imageUrl)
            itemView.setOnClickListener { showVideo(mPromo.videoUrl) }

        }
    }
}