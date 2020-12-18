package com.nikolaev.testcaseapp.network

import android.content.Context
import com.nikolaev.testcaseapp.R
import com.nikolaev.testcaseapp.model.exceptions.NoInternetException
import com.nikolaev.testcaseapp.model.exceptions.TestCaseException

object ErrorMapper {

    private lateinit var context: Context

    fun init(context: Context) {
        this@ErrorMapper.context = context
    }

    fun noInternetException() = NoInternetException(context.getString(R.string.noInternetConnection))

    fun timeoutException() = TestCaseException(context.getString(R.string.timeoutError))

    fun mapError(ex: Throwable): TestCaseException {
        return TestCaseException(context.getString(R.string.smthWentWrong))
    }
}