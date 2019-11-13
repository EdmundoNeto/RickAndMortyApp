package com.rickymortyapp.paging

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.rickmortyapp.domain.model.CharacterDetail

class CharacterPagedListAdapter(private val listener : ItemClickedLister) : PagedListAdapter<CharacterDetail, CharacterViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder = CharacterViewHolder.create(parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val characterInPosition = getItem(position)
        holder.binding.character = characterInPosition
        holder.itemView.setOnClickListener { listener.onItemClicked(characterInPosition) }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CharacterDetail>() {
            override fun areItemsTheSame(oldItem: CharacterDetail, newItem: CharacterDetail):
                    Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CharacterDetail, newItem: CharacterDetail):
                    Boolean = oldItem.name == newItem.name
        }
    }

    interface ItemClickedLister {
        fun onItemClicked( character: CharacterDetail? )
    }

}