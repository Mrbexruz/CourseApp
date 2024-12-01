package com.example.codialapp.fragments.Groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.codialapp.databinding.FragmentGroupAddBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Kurslar
import com.example.codialapp.models.Mentorlar


class GroupAddFragment : Fragment() {
    lateinit var mentorList : ArrayList<String>
    lateinit var myDb: MyDb
    lateinit var abs : Mentorlar
    private val binding by lazy { FragmentGroupAddBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val p = arguments?.getSerializable("alisher") as Kurslar
        myDb = MyDb(requireContext())
        vaqti()
        kunlari()
        mentorlar()

        binding.mentor.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mentorList)
        binding.vaqti.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, vaqti())
        binding.kunlari.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, kunlari())

        var vaqt = ""
        binding.vaqti.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                vaqt = vaqti()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

        var kun = ""
        binding.kunlari.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                kun = kunlari()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
        binding.mentor.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (mentorlar in myDb.showMentor()) {
                    if ("${mentorlar.name} ${mentorlar.lastName}"==mentorList[position]){
                        abs = mentorlar
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
        binding.btnSave.setOnClickListener {
            val grupalar = Grupalar(
                binding.nameGroup.text.toString(),
                abs,
                kun,
                vaqt,
                0
            )
            println(grupalar)
            myDb.addGroup(grupalar)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.chiqish.setOnClickListener {  requireActivity().supportFragmentManager.popBackStack() }

        return binding.root
    }




        fun vaqti():ArrayList<String>{
            val list = ArrayList<String>()
            list.add("from 8:00am to 10:00am")
            list.add("from 10:00am to  12:00am")
            list.add("from 02:00am to 04:00am ")
            list.add("from 04:00am to 06:00am ")
            return list
        }

        fun kunlari():ArrayList<String>{
            val list = ArrayList<String>()
            println("alisher")
            list.add("Monday, Wednesday, Friday")
            list.add("Tuesday, Thursday , Saturday")
            return list
        }
        fun mentorlar(){
            val p = arguments?.getSerializable("alisher") as Kurslar
            mentorList = ArrayList()
            myDb = MyDb(requireContext())
            for (mentorlar in myDb.showMentor()) {
                println(mentorlar)
                if (mentorlar.kurs_id?.id==p.id){
                    println(mentorlar)
                    mentorList.add("${mentorlar.name} ${mentorlar.lastName}")
                }
            }


    }

}