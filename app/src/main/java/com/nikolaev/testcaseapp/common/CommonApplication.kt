package com.nikolaev.testcaseapp.common

import android.app.Application
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CommonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        Realm.init(this)
        startKoin {
            androidContext(this@CommonApplication)
            modules(
                    datastoreModule,
                    useCasesModule
            )
            logger(KoinLogger())
        }
    }

    private fun initTimber() {
        val tree = if (BuildConfig.DEBUG) Timber.DebugTree() else TimberReleaseTree()
        Timber.plant(tree)
    }
}