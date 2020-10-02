package com.pollack.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

//Show back button in toolbar (such as when in a detail view)
fun Fragment.showBackButton() {
    (requireActivity() as AppCompatActivity)?.let {
        it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

//Hide back button in toolbar
fun Fragment.hideBackButton() {
    (requireActivity() as AppCompatActivity)?.let {
        it.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}