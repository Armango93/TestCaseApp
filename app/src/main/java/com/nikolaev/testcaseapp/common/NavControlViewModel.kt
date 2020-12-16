package com.nikolaev.testcaseapp.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController

abstract class NavControlViewModel(app: Application) : AndroidViewModel(app) {
    var navController: NavController? = null
}