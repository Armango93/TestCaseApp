package com.nikolaev.testcaseapp.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikolaev.testcaseapp.R
import com.nikolaev.testcaseapp.findNavController

/**
 * Developed by Magora Team (magora-systems.com)
 * 2020
 *
 * @author Andrey Nikolaev
 */
class MainActivity: AppCompatActivity()  {

    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}