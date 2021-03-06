package com.nikolaev.testcaseapp.network

import com.nikolaev.testcaseapp.model.BaseResponse
import com.nikolaev.testcaseapp.model.Employee
import retrofit2.http.GET

/**
 * Developed by Magora Team (magora-systems.com)
 * 2020
 *
 * @author Andrey Nikolaev
 */
interface RestClient {
    @GET("65gb/static/raw/master/testTask.json")
    suspend fun getEmployeeData(): BaseResponse<List<Employee>>
}