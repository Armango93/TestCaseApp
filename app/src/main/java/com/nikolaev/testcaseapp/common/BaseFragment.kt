package com.nikolaev.testcaseapp.common

import androidx.fragment.app.Fragment
import androidx.navigation.NavController

/**
 * Developed by Magora Team (magora-systems.com)
 * 2020
 *
 * @author Andrey Nikolaev
 */
abstract class BaseFragment: Fragment() {
    abstract fun navController(): NavController
    abstract val viewModel: NavControlViewModel?

    override fun onResume() {
        super.onResume()
        if (viewModel != null) {
            viewModel!!.navController = navController()
        }
    }
}