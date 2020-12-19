package com.nikolaev.testcaseapp.ui.specialty_employee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.nikolaev.testcaseapp.*
import com.nikolaev.testcaseapp.model.Employee

class EmployeeListAdapter(
    private val onClick: ((Employee) -> Unit)? = null
) : RecyclerView.Adapter<EmployeeListAdapter.EmployeeHolder>() {

    private val employeeList = ArrayList<Employee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        return EmployeeHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.employee_item, parent, false)
        ).apply {
            if (onClick != null) {
                itemView.setOnClickListener {
                    val adapterPosition = this.adapterPosition
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        employeeList[adapterPosition]?.let(onClick)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.bind(employeeList[position])
    }

    override fun getItemCount() = employeeList.size

    fun setItems(list: Collection<Employee>) {
        employeeList.clear()
        employeeList.addAll(list)
        notifyDataSetChanged()
    }

    inner class EmployeeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val employeeName = itemView.findViewById<TextView>(R.id.specialtyName)
        private val employeeDateOfBirth = itemView.findViewById<TextView>(R.id.specialtyId)

        fun bind(data: Employee) {
            employeeName.text = String.format(
                itemView.context.getString(R.string.full_name_template),
                data.firstName?.formatName(),
                data.lastName?.formatName()
            )

            if (!data.birthday.isNullOrEmpty()) {
                employeeDateOfBirth.text = data.birthday?.formatDateOfBirth(itemView.context)
            } else {
                employeeDateOfBirth.text = itemView.context.getString(R.string.no_date_stub)
            }
        }
    }

}