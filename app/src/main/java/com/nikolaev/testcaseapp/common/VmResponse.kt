package com.nikolaev.testcaseapp.common

import com.nikolaev.testcaseapp.model.CoreResponse

class VmResponse<T>(
        val status: Status,
        override var success: T? = null,
        override var error: Throwable? = null
): CoreResponse<T>() {

    companion object {
        fun <T> loading() = VmResponse<T>(Status.LOADING)
        fun <T> success(success: T?) = VmResponse<T>(Status.SUCCESS, success, null)
        fun <T> error(error: Throwable) = VmResponse<T>(Status.ERROR, null, error)
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}