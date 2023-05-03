package com.countlesswrongs.myshoppinglist.presentation.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.countlesswrongs.myshoppinglist.R
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    companion object {
        const val VIEW_TYPE_ENABLED = 101
        const val VIEW_TYPE_DISABLED = 102
        const val MAX_POOL_SIZE = 15
    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null

    var shopItemList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutId =
            when (viewType) {
                VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
                VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
                else -> throw RuntimeException("Unknown view type: $viewType")
            }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopItemList[position]
        holder.textViewItemName.text = shopItem.name
        holder.textViewItemCount.text = shopItem.amount.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
    }

    override fun getItemCount(): Int {
        return shopItemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopItemList[position].enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewItemName: TextView = view.findViewById(R.id.textViewItemName)
        val textViewItemCount: TextView = view.findViewById(R.id.textViewItemCount)
    }

}
