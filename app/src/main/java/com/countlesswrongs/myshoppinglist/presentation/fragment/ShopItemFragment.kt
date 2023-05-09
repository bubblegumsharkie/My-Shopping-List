package com.countlesswrongs.myshoppinglist.presentation.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.countlesswrongs.myshoppinglist.R
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import com.countlesswrongs.myshoppinglist.presentation.activity.ShopItemActivity
import com.countlesswrongs.myshoppinglist.presentation.viewmodel.ShopItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutAmount: TextInputLayout
    private lateinit var editTextName: EditText
    private lateinit var editTextAmount: EditText
    private lateinit var buttonSave: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
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
        viewModel.currentShopItem.observe(viewLifecycleOwner) {
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
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun listenToErrors() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.errror_name)
            } else {
                null
            }
            textInputLayoutName.error = message
        }
        viewModel.errorInputAmount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.errror_amount)
            } else {
                null
            }
            textInputLayoutAmount.error = message
        }
    }

    private fun parseIntent() {
//        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
//            throw RuntimeException("Screen mode extra was not found")
//        }
//        val stringExtra = intent.getStringExtra(EXTRA_SCREEN_MODE)
//        if (stringExtra != MODE_EDIT && stringExtra != MODE_ADD) {
//            throw RuntimeException("Unknown screen mode: $stringExtra")
//        }
//        screenMode = stringExtra
//        if (screenMode == MODE_EDIT) {
//            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
//                throw RuntimeException("Shop item id was not found")
//            }
//            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
//        }
    }

    private fun initViews(view: View) {
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName)
        textInputLayoutAmount = view.findViewById(R.id.textInputLayoutAmount)
        editTextName = view.findViewById(R.id.editTextName)
        editTextAmount = view.findViewById(R.id.editTextAmount)
        buttonSave = view.findViewById(R.id.buttonSave)
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment(MODE_ADD)
        }

        fun newInstanceEditItem(shopItemID: Int): ShopItemFragment {
            return ShopItemFragment(MODE_EDIT, shopItemID)
        }

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
