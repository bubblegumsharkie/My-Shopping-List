package com.countlesswrongs.myshoppinglist.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.countlesswrongs.myshoppinglist.R
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import com.countlesswrongs.myshoppinglist.presentation.viewmodel.ShopItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutAmount: TextInputLayout
    private lateinit var editTextName: EditText
    private lateinit var editTextAmount: EditText
    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        pickLaunchMode()
        observeViewModel()
    }

    private fun pickLaunchMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun observeViewModel() {
        listenToFieldsTextChangeAndResetErrors()
        listenToErrors()
        listenShouldCloseScreen()
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            val name = editTextName.text?.toString()
            val amount = editTextAmount.text?.toString()
            viewModel.addShopItem(name, amount)
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.currentShopItem.observe(this) {
            editTextName.setText(it.name)
            editTextAmount.setText(it.amount.toString())
        }
        buttonSave.setOnClickListener {
            val name = editTextName.text?.toString()
            val amount = editTextAmount.text?.toString()
            viewModel.editShopItem(name, amount)
        }
    }

    private fun listenToFieldsTextChangeAndResetErrors() {
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayoutName.error = null
                viewModel.resetInputNameError()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        editTextAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInputAmountError()
                textInputLayoutAmount.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun listenShouldCloseScreen() {
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun listenToErrors() {
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.errror_name)
            } else {
                null
            }
            textInputLayoutName.error = message
        }
        viewModel.errorInputAmount.observe(this) {
            val message = if (it) {
                getString(R.string.errror_amount)
            } else {
                null
            }
            textInputLayoutAmount.error = message
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Screen mode extra was not found")
        }
        val stringExtra = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (stringExtra != MODE_EDIT && stringExtra != MODE_ADD) {
            throw RuntimeException("Unknown screen mode: $stringExtra")
        }
        screenMode = stringExtra
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Shop item id was not found")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        textInputLayoutName = findViewById(R.id.textInputLayoutName)
        textInputLayoutAmount = findViewById(R.id.textInputLayoutAmount)
        editTextName = findViewById(R.id.editTextName)
        editTextAmount = findViewById(R.id.editTextAmount)
        buttonSave = findViewById(R.id.buttonSave)
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddShopItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditShopItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }

}
