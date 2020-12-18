package com.nikolaev.testcaseapp.network

import com.nikolaev.testcaseapp.handleError
import com.nikolaev.testcaseapp.model.BaseResponse
import com.nikolaev.testcaseapp.model.CoreResponse
import com.nikolaev.testcaseapp.model.Employee

class RestClientImpl(private val restClient: RestClient): EmployeeRepository {
    override suspend fun getEmployeeData(): CoreResponse<List<Employee>> {
        return handleError { restClient.getEmployeeData() }
    }
}