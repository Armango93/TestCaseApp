package com.nikolaev.testcaseapp.ui.specialty_employee

import com.nikolaev.testcaseapp.database.EmployeeRepo
import com.nikolaev.testcaseapp.model.CoreResponse
import com.nikolaev.testcaseapp.model.Employee
import com.nikolaev.testcaseapp.model.Specialty
import com.nikolaev.testcaseapp.network.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeListUseCase(
    val dbRepository: EmployeeRepo
) {

    suspend fun getAllEmployeesBySpecialty(specialty: Specialty) = withContext(Dispatchers.IO) {
        val result = dbRepository.getAllEmployeesBySpecialty(specialty)

        if (!result.isNullOrEmpty()) {
            CoreResponse(success = result)
        } else {
            CoreResponse(error = Throwable(""))
        }
    }
}