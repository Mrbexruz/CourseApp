package com.example.codialapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialapp.databinding.ItemStudentBinding
import com.example.codialapp.fragments.Groups.StudentFragment
import com.example.codialapp.models.Talabalar

class StudentAdapter(val rvAction: StudentAdapter.RvAction, val list:ArrayList<Talabalar>): RecyclerView.Adapter<StudentAdapter.Vh>() {
    inner class Vh(var itemStudentBinding: ItemStudentBinding): RecyclerView.ViewHolder(itemStudentBinding.root){
        fun onBind(talabalar: Talabalar, position: Int){
            itemStudentBinding.name.text = talabalar.firstName
            itemStudentBinding.lastName.text = talabalar.lastName
            itemStudentBinding.time.text = talabalar.day

            itemStudentBinding.edit.setOnClickListener {
                rvAction.editClick(talabalar)
            }
            itemStudentBinding.delete.setOnClickListener {
                rvAction.deleteClick(talabalar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemStudentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }
    interface RvAction{
        fun editClick(talabalar: Talabalar)
        fun deleteClick(talabalar:Talabalar)
    }
}