package com.nikolaev.testcaseapp.model

open class CoreResponse<T> (open var success: T? = null, open var error: Throwable? = null){
    fun isSuccess() = success != null
}