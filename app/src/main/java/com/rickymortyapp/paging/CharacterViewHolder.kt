package com.rickymortyapp.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickymortyapp.databinding.ItemCharacterViewholderBinding

class CharacterViewHolder(val binding: ItemCharacterViewholderBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create (parent : ViewGroup) : CharacterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return CharacterViewHolder(ItemCharacterViewholderBinding.inflate(inflater))
        }
    }

}