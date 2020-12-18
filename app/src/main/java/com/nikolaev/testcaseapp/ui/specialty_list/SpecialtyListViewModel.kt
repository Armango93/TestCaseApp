package com.nikolaev.testcaseapp.ui.specialty_list

import android.app.Application
import androidx.navigation.NavDirections
import com.nikolaev.testcaseapp.common.NavControlViewModel
import com.nikolaev.testcaseapp.common.SingleLiveEvent
import com.nikolaev.testcaseapp.common.VmResponse
import com.nikolaev.testcaseapp.model.Specialty
import com.nikolaev.testcaseapp.parseResult
import com.nikolaev.testcaseapp.parseResultSuspend
import kotlinx.coroutines.*

class SpecialtyListViewModel(
    private val app: Application,
    private val specialtyListUseCase: SpecialtyListUseCase
) : NavControlViewModel(app), CoroutineScope {

    override val coroutineContext = Dispatchers.Main + Job()

    val specialtyListLivaData = SingleLiveEvent<VmResponse<List<Specialty>>>()

    fun getAllSpecialties() {
        launch {
            specialtyListLivaData.value = VmResponse.loading()
            specialtyListUseCase.getAllEmployeeList().parseResultSuspend({
                specialtyListLivaData.value = VmResponse.success(
                    specialtyListUseCase.getUniqueSpecialties()
                )
            }, {ex ->
                specialtyListLivaData.value = VmResponse.error(ex)
            })
        }
    }

    fun toSpecialtyEmpoyeesScreen(specialty: Specialty) {
        navController?.navigate(
            SpecialtyListFragmentDirections.actionSpecialtyListFragmentToEmployeeListFragment(specialty)
        )
    }
}