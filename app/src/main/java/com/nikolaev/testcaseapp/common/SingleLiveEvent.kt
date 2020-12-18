package com.nikolaev.testcaseapp.common

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Timber.w("Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer<T> {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    private var foreverObserver: Observer<in T>? = null
    private var innerForeverObserver: Observer<in T>? = null

    override fun observeForever(observer: Observer<in T>) {
        foreverObserver = observer
        innerForeverObserver = Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        }
        super.observeForever(innerForeverObserver!!)
    }

    override fun removeObserver(observer: Observer<in T>) {
        super.removeObserver(observer)
        if (observer == foreverObserver && innerForeverObserver != null) {
            removeObserver(innerForeverObserver!!)
            foreverObserver = null
            innerForeverObserver = null
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    override fun postValue(value: T) {
        mPending.set(true)
        super.postValue(value)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}