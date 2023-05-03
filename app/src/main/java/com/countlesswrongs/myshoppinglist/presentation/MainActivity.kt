package com.countlesswrongs.myshoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.countlesswrongs.myshoppinglist.R
import com.countlesswrongs.myshoppinglist.presentation.adapter.ShopListAdapter
import com.countlesswrongs.myshoppinglist.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.shopItemList = it
        }
    }

    private fun setupRecyclerView() {
        val recyclerViewItemsList = findViewById<RecyclerView>(R.id.recyclerViewItemsList)
        shopListAdapter = ShopListAdapter()

        with(recyclerViewItemsList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeShopItemStatus(it)
        }

    }

}

