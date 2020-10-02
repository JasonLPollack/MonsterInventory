package com.pollack.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.showBackButton() {
    (requireActivity() as AppCompatActivity)?.let {
        it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

fun Fragment.hideBackButton() {
    (requireActivity() as AppCompatActivity)?.let {
        it.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}