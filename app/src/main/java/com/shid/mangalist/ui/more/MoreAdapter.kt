package com.shid.mangalist.ui.more

import android.app.Activity
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shid.mangalist.R
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.ui.home.MoreDiffUtils


class MoreAdapter constructor(var activity2: Activity) :
    PagingDataAdapter<AnimeListResponse, MoreAdapter.MoreViewHolder>(MoreDiffUtils) {
     companion object{
         lateinit var activity:Activity
     }

     init {
         activity = activity2
     }




    override fun onBindViewHolder(holder: MoreViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoreViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_more, parent, false)
        return MoreViewHolder(view)
    }

     class MoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.image)


        init {
            itemView.layoutParams = RecyclerView.LayoutParams(
                getScreenWidth(activity) * 0.85f.toInt(),
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
        }


        fun bindTo(anime: AnimeListResponse) {
            image.load(anime.imageUrl)
        }

         fun getScreenWidth(activity: Activity):Int{
             return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                 val windowMetrics = activity.windowManager.currentWindowMetrics
                 val insets: Insets = windowMetrics.windowInsets
                     .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                 windowMetrics.bounds.width() - insets.left - insets.right
             } else {
                 val displayMetrics = DisplayMetrics()
                 activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                 displayMetrics.widthPixels
             }
         }
    }
}