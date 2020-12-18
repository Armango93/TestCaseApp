package com.nikolaev.testcaseapp.ui.specialty_list

import com.nikolaev.testcaseapp.database.EmployeeRepo
import com.nikolaev.testcaseapp.model.CoreResponse
import com.nikolaev.testcaseapp.model.Employee
import com.nikolaev.testcaseapp.model.Specialty
import com.nikolaev.testcaseapp.network.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpecialtyListUseCase(
    val netRepository: EmployeeRepository,
    val dbRepository: EmployeeRepo
) {
    suspend fun getAllEmployeeList(): CoreResponse<List<Employee>> = withContext(Dispatchers.IO) {
        val result = netRepository.getEmployeeData()

        result.success?.let { dbRepository.saveEmployeeList(it) }
        result
        }

    suspend fun getUniqueSpecialties(): List<Specialty>  = withContext(Dispatchers.IO) {
        dbRepository.getAllUniqueSpecialties()
    }
}