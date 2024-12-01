package com.example.codialapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialapp.databinding.ItemGroupBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Talabalar

class GroupsAdapter(var rvAction5: RvAction5, var list: ArrayList<Grupalar>, var myDb: MyDb): RecyclerView.Adapter<GroupsAdapter.Vh>() {

    inner class Vh(var itemGroupBinding: ItemGroupBinding): RecyclerView.ViewHolder(itemGroupBinding.root){
        fun onBind(grupalar: Grupalar, position: Int){
            itemGroupBinding.name.text = grupalar.name
            itemGroupBinding.count.text = counter(grupalar, myDb)

            itemGroupBinding.view.setOnClickListener {
                rvAction5.viewClick(grupalar, position)
            }
            itemGroupBinding.edit.setOnClickListener {
                rvAction5.editClick(grupalar, position)
            }
            itemGroupBinding.delete.setOnClickListener {
                rvAction5.deleteClick(grupalar, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction5 {
        fun viewClick(grupalar: Grupalar, position: Int)
        fun editClick(grupalar: Grupalar, position: Int)
        fun deleteClick(grupalar: Grupalar, position: Int)
    }

    fun counter(grupalar: Grupalar, myDbHelper1: MyDb) : String{
        val list = ArrayList<Talabalar>()
        myDbHelper1.showStudents().forEach {
            if (it.groupId?.id == grupalar.id){
                list.add(it)
            }
        }
        return "Number of students: ${list.size} "
    }
}