package com.shid.mangalist.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shid.mangalist.R
import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.utils.custom.CircleImageView

class CharacterAdapter(private val showDetail: (character: CharactersListResponse) -> Unit) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var listData = ArrayList<CharactersListResponse>()
    private val limit: Int = 20

    fun setData(newListData: List<CharactersListResponse>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_anime_list,
            parent,
            false
        )
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = listData[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return if (listData.size > limit)
            limit
        else
            listData.size
    }

    inner class CharacterViewHolder(itemView:View) :
        RecyclerView.ViewHolder(itemView) {
        val image_character:CircleImageView = itemView.findViewById(R.id.profile_image)
        val name_character:AppCompatTextView = itemView.findViewById(R.id.txt_name)
        fun bind(character: CharactersListResponse) {
            image_character.load(character.imageUrl)
            name_character.text = character.name
            itemView.setOnClickListener { showDetail(character) }
        }
    }
}