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
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapter.shopItemList = it
        }
    }

    private fun setupRecyclerView() {
        val recyclerViewItemsList = findViewById<RecyclerView>(R.id.recyclerViewItemsList)
        adapter = ShopListAdapter()
        recyclerViewItemsList.adapter = adapter
    }


}
