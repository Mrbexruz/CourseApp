package com.example.codialapp.fragments.Groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.codialapp.adapters.utils.MyData
import com.example.codialapp.databinding.FragmentEditGroupBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Mentorlar

class EditGroupFragment : Fragment() {
    lateinit var myDb: MyDb
    lateinit var mentorList: ArrayList<String>
    private lateinit var codial: Mentorlar
    private val binding by lazy { FragmentEditGroupBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val id1 = arguments?.getInt("edit") ?: -1
        if (id1 == -1) {
            Toast.makeText(requireContext(), "Group ID not found", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
            return binding.root
        }

        myDb = MyDb(requireContext())
        var isOpened = 0

        // Guruh ma'lumotlarini olish
        val currentGroup = myDb.showGroup().find { it.id == id1 }
        currentGroup?.let { group ->
            binding.nameGroup.setText(group.name)
            isOpened = group.ochilganmi
        }

        // Spinner adapterlari
        mentorlar()
        val vaqtList = vaqti()
        val kunList = kunlari()

        binding.mentor.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mentorList)
        binding.vaqti.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, vaqtList)
        binding.kunlari.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, kunList)

        // Spinnerlar tanlovi
        var vaqt = vaqtList.firstOrNull() ?: ""
        var kun = kunList.firstOrNull() ?: ""

        binding.vaqti.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                vaqt = vaqtList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.kunlari.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                kun = kunList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.mentor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedMentor = mentorList[position]
                codial = myDb.showMentor().find {
                    "${it.name} ${it.lastName}" == selectedMentor
                } ?: return
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Saqlash tugmasi
        binding.btnSave.setOnClickListener {
            val groupToUpdate = Grupalar(
                id1,
                binding.nameGroup.text.toString(),
                codial,
                kun,
                vaqt,
                isOpened
            )

            myDb.editGroup(groupToUpdate)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Chiqish tugmasi
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    // Spinner uchun vaqtlar
    private fun vaqti(): ArrayList<String> = arrayListOf(
        "from 8:00am to  10:00am ",
        "from 10:00am to  12:00am ",
        "from 02:00pm to  04:00pm ",
        "from 04:00pm to  06:00pm ",
    )

    // Spinner uchun kunlar
    private fun kunlari(): ArrayList<String> = arrayListOf(
        "Monday, Wednesday, Friday",
        "Tuesday, Thursday , Saturday"
    )

    // Mentorlar ro'yxati
    private fun mentorlar() {
        myDb = MyDb(requireContext())
        mentorList = ArrayList()
        MyData.kurslar?.let { kurs ->
            myDb.showMentor().forEach { mentor ->
                if (mentor.kurs_id?.id == kurs.id) {
                    mentorList.add("${mentor.name} ${mentor.lastName}")
                }
            }
        }
    }
}
