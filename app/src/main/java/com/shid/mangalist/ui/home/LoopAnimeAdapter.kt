package com.shid.mangalist.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.shid.mangalist.R
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse

class LoopAnimeAdapter(
    context: Context,
    itemList: ArrayList<AnimeListResponse>,
    isInfinite: Boolean,
    private val showDetail: (id: Int) -> Unit
) : LoopingPagerAdapter<AnimeListResponse>(context, itemList, isInfinite) {

    //This method will be triggered if the item View has not been inflated before.
    override fun inflateView(
        viewType: Int,
        container: ViewGroup,
        listPosition: Int
    ): View {
        return LayoutInflater.from(context).inflate(R.layout.item_pager, container, false)
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    override fun bindView(
        convertView: View,
        listPosition: Int,
        viewType: Int
    ) {
        //convertView.findViewById<View>(R.id.image).setBackgroundColor(context.resources.getColor(getBackgroundColor(listPosition)))
        val title = convertView.findViewById<TextView>(R.id.pager_title)
        title.text = itemList?.get(listPosition)?.title ?: "Network error"

        val score = convertView.findViewById<TextView>(R.id.pager_score)
        score.text = itemList?.get(listPosition)?.score.toString()

        val poster = convertView.findViewById<ImageView>(R.id.img_pager)
        poster.load(itemList?.get(listPosition)?.imageUrl)

        convertView.setOnClickListener(View.OnClickListener {
            itemList?.get(listPosition)?.id?.let { it1 -> showDetail(it1) }
        })
    }


}