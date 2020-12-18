package com.nikolaev.testcaseapp.model.exceptions

open class TestCaseException(
        message: String? = null,
        var code: Int? = -1
): Exception(message) {
}