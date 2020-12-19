package com.nikolaev.testcaseapp.ui.specialty_employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nikolaev.testcaseapp.R
import com.nikolaev.testcaseapp.common.BaseFragment
import com.nikolaev.testcaseapp.common.VmResponse
import com.nikolaev.testcaseapp.findNavController
import com.nikolaev.testcaseapp.gone
import com.nikolaev.testcaseapp.model.Employee
import com.nikolaev.testcaseapp.visible
import kotlinx.android.synthetic.main.fragment_employee_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmployeeListFragment : BaseFragment() {
    override fun navController() = findNavController()
    override val viewModel by viewModel<EmployeeListViewModel>()

    private val adapter by lazy { EmployeeListAdapter(onClick) }

    private val args by navArgs<EmployeeListFragmentArgs>()
    val specialty by lazy { args.specialty }

    private val onClick: (Employee) -> Unit = {
        viewModel.toEmployeeProfile(it)
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.getEmployeesBySpecialty(specialty)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employee_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout.setOnRefreshListener(refreshListener)
        viewModel.getEmployeesBySpecialty(specialty)

        viewModel.employeeListLivaData.observe(viewLifecycleOwner, Observer {
            if (it.status == VmResponse.Status.LOADING) progress.visible() else progress.gone()

            if (it.status == VmResponse.Status.SUCCESS) {
                if (refreshLayout.isRefreshing) refreshLayout.isRefreshing = false
                it.success?.let { list -> initRecycler(list) }
            }

            if (it.status == VmResponse.Status.ERROR) {
                if (refreshLayout.isRefreshing) refreshLayout.isRefreshing = false
                Toast.makeText(
                    context,
                    it.error?.message ?: getString(R.string.smthWentWrong),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun initRecycler(items: Collection<Employee>) {
        employeeRecycler.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        employeeRecycler.adapter = adapter
        adapter.setItems(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        employeeRecycler.adapter = null
        refreshLayout.setOnRefreshListener(null)
    }
}