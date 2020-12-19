package com.nikolaev.testcaseapp.ui.employee_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.nikolaev.testcaseapp.*
import com.nikolaev.testcaseapp.common.BaseFragment
import com.nikolaev.testcaseapp.common.NavControlViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

class ProfileFragment : BaseFragment() {
    override fun navController() = findNavController()
    override val viewModel: NavControlViewModel? = null

    private val args by navArgs<ProfileFragmentArgs>()
    private val employee by lazy { args.employee }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        employee.avatarUrl?.let { img -> profileImage.setImage(img, requireContext()) }
        fillFullName()
        fillDataOfBirthAndAge()
        fillSpecialties()
    }

    private fun fillSpecialties() {
        if (!employee.specialtyList.isNullOrEmpty()) {
            var specialties = StringBuilder()
            employee.specialtyList?.forEach {spec ->
                if (specialties.isNotBlank()) {
                    spec.specialtyName?.let { specialties.append(", $it") }
                } else {
                    spec.specialtyName?.let { specialties.append(it) }
                }
            }
            specialtiesTextView.text = specialties.toString()
        }
    }

    private fun fillFullName() {
        val firstName = employee.firstName
        val lastName = employee.lastName
        fullName.text = String.format(
            getString(R.string.full_name_template),
            firstName?.formatName(),
            lastName?.formatName()
        )
    }

    private fun fillDataOfBirthAndAge() {
        val res = employee.birthday?.formatDateOfBirth(requireContext())
        if (!res.isNullOrEmpty()) {
            dateOfBirth.text = res
            age.text = getAgeOfEmployee(res)
        } else {
            dateOfBirth.text = getString(R.string.no_date_stub)
            age.text = getString(R.string.no_date_stub)
        }
    }

    private fun getAgeOfEmployee(bithday: String): String {
        val bdArr = bithday.split(".")

        val day = bdArr[0].toInt()
        val month = bdArr[1].toInt() - 1
        val year = bdArr[2].toInt()

        val dobCalen = Calendar.getInstance()
        val todayCalen = Calendar.getInstance()

        dobCalen.set(year, month, day)

        var age = todayCalen[Calendar.YEAR] - dobCalen[Calendar.YEAR]
        if (todayCalen[Calendar.DAY_OF_YEAR] < dobCalen[Calendar.DAY_OF_YEAR]) age--

        return String.format(getString(R.string.age_template), age.toString())
    }
}


