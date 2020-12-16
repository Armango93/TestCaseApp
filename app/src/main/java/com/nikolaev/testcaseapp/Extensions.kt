package com.nikolaev.testcaseapp

import android.app.Activity
import android.view.View
import androidx.navigation.Navigation

/**
 * Developed by Magora Team (magora-systems.com)
 * 2020
 *
 * @author Andrey Nikolaev
 */


fun Activity.findNavController() = Navigation.findNavController(this, R.id.navHost)

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}