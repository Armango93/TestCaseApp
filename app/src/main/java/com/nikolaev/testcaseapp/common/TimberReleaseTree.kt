package com.nikolaev.testcaseapp.common

import android.util.Log
import timber.log.Timber

class TimberReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            Log.VERBOSE, Log.DEBUG -> {

            }
            Log.INFO, Log.WARN, Log.ERROR -> {

            }
            else -> {
                // Ignoring
            }
        }
    }
}
