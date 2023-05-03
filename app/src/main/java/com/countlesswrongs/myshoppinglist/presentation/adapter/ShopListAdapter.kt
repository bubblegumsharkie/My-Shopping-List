package com.countlesswrongs.myshoppinglist.presentation.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.countlesswrongs.myshoppinglist.R
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopItemList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_shop_enabled, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopItemList[position]
        holder.textViewItemName.text = shopItem.name
        holder.textViewItemCount.text = shopItem.amount.toString()
    }

    override fun getItemCount(): Int {
        return shopItemList.size
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewItemName: TextView = view.findViewById<TextView>(R.id.textViewItemName)
        val textViewItemCount: TextView = view.findViewById<TextView>(R.id.textViewItemCount)
    }

}
