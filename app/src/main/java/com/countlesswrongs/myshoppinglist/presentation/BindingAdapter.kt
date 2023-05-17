package com.countlesswrongs.myshoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.countlesswrongs.myshoppinglist.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("setErrorInputName")
fun bindErrorInputName(view: TextInputLayout, argument: Boolean) {
    val message = if (argument) {
        view.context.getString(R.string.errror_name)
    } else {
        null
    }
    view.error = message
}
@BindingAdapter("setErrorInputAmount")
fun bindErrorInputAmount(view: TextInputLayout, argument: Boolean) {
    val message = if (argument) {
        view.context.getString(R.string.errror_amount)
    } else {
        null
    }
    view.error = message
}