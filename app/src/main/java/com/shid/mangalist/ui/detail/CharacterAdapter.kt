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

class CharacterAdapter() :
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
            R.layout.list_item_cast,
            parent, false)
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
        private val imageCharacter:CircleImageView = itemView.findViewById(R.id.image_cast)
        private val nameCharacter:AppCompatTextView = itemView.findViewById(R.id.name_cast)
        fun bind(character: CharactersListResponse) {
            imageCharacter.load(character.imageUrl)
            nameCharacter.text = character.name



        }
    }
}