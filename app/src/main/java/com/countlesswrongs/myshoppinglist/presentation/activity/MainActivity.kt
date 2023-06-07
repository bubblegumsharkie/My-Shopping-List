package com.countlesswrongs.myshoppinglist.presentation.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.countlesswrongs.myshoppinglist.R
import com.countlesswrongs.myshoppinglist.databinding.ActivityMainBinding
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import com.countlesswrongs.myshoppinglist.presentation.ShopApplication
import com.countlesswrongs.myshoppinglist.presentation.adapter.ShopListAdapter
import com.countlesswrongs.myshoppinglist.presentation.fragment.ShopItemFragment
import com.countlesswrongs.myshoppinglist.presentation.viewmodel.MainViewModel
import com.countlesswrongs.myshoppinglist.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ShopApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        getAndObserveViewModel()
        setOnClickListeners()

        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.countlesswrongs.myshoppinglist/shop_items"),
                null,
                null,
                null,
                null,
                null
            )

            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val amount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0

                val shopItem = ShopItem(name, amount, enabled, id)
                Log.d("MainActivity", shopItem.toString())
            }
            cursor?.close()
        }
    }

    private fun setOnClickListeners() {
        binding.buttonAddItem.setOnClickListener {
            if (isVerticalMode()) {
                val intent = ShopItemActivity.newIntentAddShopItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun getAndObserveViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isVerticalMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        shopListAdapter = ShopListAdapter()

        with(binding.recyclerViewItemsList) {
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
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(binding.recyclerViewItemsList)
    }

    private fun setupSwipeListener(recyclerViewItemsList: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerViewItemsList)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            if (isVerticalMode()) {
                val intent = ShopItemActivity.newIntentEditShopItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeShopItemStatus(it)
        }
    }
}
