package com.nikolaev.testcaseapp.ui.specialty_employee

import android.app.Application
import com.nikolaev.testcaseapp.common.NavControlViewModel
import com.nikolaev.testcaseapp.common.SingleLiveEvent
import com.nikolaev.testcaseapp.common.VmResponse
import com.nikolaev.testcaseapp.model.Employee
import com.nikolaev.testcaseapp.model.Specialty
import com.nikolaev.testcaseapp.parseResult
import kotlinx.coroutines.*

class EmployeeListViewModel(
    private val app: Application,
    private val employeeListUseCase: EmployeeListUseCase
) : NavControlViewModel(app), CoroutineScope {

    override val coroutineContext = Dispatchers.Main + Job()

    val employeeListLivaData = SingleLiveEvent<VmResponse<List<Employee>>>()


    fun toEmployeeProfile(employee: Employee) {
        navController?.navigate(
            EmployeeListFragmentDirections.actionEmployeeListFragmentToProfileFragment(employee))
    }

    fun getEmployeesBySpecialty(specialty: Specialty) {
        employeeListLivaData.value = VmResponse.loading()
        launch {
            employeeListUseCase.getAllEmployeesBySpecialty(specialty).parseResult({
                employeeListLivaData.value = VmResponse.success(it)
            }, {ex ->
                employeeListLivaData.value = VmResponse.error(ex)
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}