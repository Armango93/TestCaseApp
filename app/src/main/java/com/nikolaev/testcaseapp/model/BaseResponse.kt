package com.nikolaev.testcaseapp.model

import com.google.gson.annotations.SerializedName

class BaseResponse<T>  (
    @SerializedName("response")
    val response: T? = null
)