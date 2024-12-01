package com.example.codialapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.codialapp.databinding.ItemKursBinding
import com.example.codialapp.models.Kurslar

class CoursesAdapter( context: Context, var rvAction: RvAction, val list: ArrayList<Kurslar>):RecyclerView.Adapter<CoursesAdapter.Vh>() {
    inner class Vh(var itemKursBinding: ItemKursBinding):ViewHolder(itemKursBinding.root){

        fun onBind(kurslar: Kurslar){
            itemKursBinding.name.text = kurslar.name
            itemKursBinding.root.setOnClickListener {
                rvAction.onClick(adapterPosition , kurslar)
            }
    }

}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemKursBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    interface RvAction{
        fun onClick(position: Int,kurslar: Kurslar)
    }
}