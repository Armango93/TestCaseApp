package com.nikolaev.testcaseapp.network

import com.nikolaev.testcaseapp.model.BaseResponse
import com.nikolaev.testcaseapp.model.CoreResponse
import com.nikolaev.testcaseapp.model.Employee

interface EmployeeRepository {
    suspend fun getEmployeeData(): CoreResponse<List<Employee>>
}
