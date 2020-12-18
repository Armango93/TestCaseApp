package com.nikolaev.testcaseapp.ui.specialty_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikolaev.testcaseapp.R
import com.nikolaev.testcaseapp.model.Specialty

class SpecialtyListAdapter(
    private val onClick: ((Specialty) -> Unit)? = null
) : RecyclerView.Adapter<SpecialtyListAdapter.SpecialtyHolder>() {

    private val specialtyList = ArrayList<Specialty>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyHolder {
        return SpecialtyHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.employee_item, parent, false)
        ).apply {
            if (onClick != null) {
                itemView.setOnClickListener {
                    val adapterPosition = this.adapterPosition
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        specialtyList[adapterPosition]?.let(onClick)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: SpecialtyHolder, position: Int) {
        holder.bind(specialtyList[position])
    }

    override fun getItemCount() = specialtyList.size

    fun setItems(list: Collection<Specialty>) {
        specialtyList.clear()
        specialtyList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SpecialtyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseName = itemView.findViewById<TextView>(R.id.specialtyName)
        private val exerciseId = itemView.findViewById<TextView>(R.id.specialtyId)

        fun bind(data: Specialty) {
            exerciseName.text = data.specialtyName
            exerciseId.text = String.format(
                itemView.context.getString(R.string.specialty_id_text),
                data.specialtyId
            )
        }
    }

}
