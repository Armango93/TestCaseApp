package com.nikolaev.testcaseapp.database

import com.nikolaev.testcaseapp.model.Employee
import com.nikolaev.testcaseapp.model.Specialty

interface EmployeeRepo {
    fun saveEmployeeList(list: List<Employee>)
    fun getAllUniqueSpecialties(): List<Specialty>
    fun getAllEmployeesBySpecialty(specialty: Specialty): List<Employee>
    fun deleteAllEmployees()
}
