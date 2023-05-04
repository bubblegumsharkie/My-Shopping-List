package com.countlesswrongs.myshoppinglist.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem

class ShopItemDiffCallback : DiffUtil.ItemCallback<ShopItem>() {

    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}