package com.example.codialapp.fragments.Groups

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.codialapp.R
import com.example.codialapp.adapters.utils.MyData
import com.example.codialapp.databinding.FragmentEditStudentBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Talabalar
import java.util.Calendar


class EditStudentFragment : Fragment() {
    private val binding by lazy { FragmentEditStudentBinding.inflate(layoutInflater) }
    lateinit var myDb: MyDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myDb = MyDb(requireContext())
        binding.ismi.setText(MyData.talabalar?.firstName)
        binding.familya.setText(MyData.talabalar?.lastName)
        binding.phoneNumber.setText(MyData.talabalar?.number)
        binding.date.text = MyData.talabalar?.day
        var date = ""

        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(), 0,
            { _, year, month, dayOfMonth ->
                date = "$dayOfMonth.${month + 1}.$year"
                binding.date.text = date
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).text = "Select"


        var a = ""
        if (date.isNotEmpty()) a = date
        else a = MyData.talabalar?.day.toString()
        binding.btnSave.setOnClickListener {
            if (binding.ismi.text.isNotEmpty() && binding.familya.text.isNotEmpty() && binding.phoneNumber.text.isNotEmpty() && binding.date.text.isNotEmpty()){
                myDb.editStudent(
                    Talabalar(
                        MyData.talabalar?.id,
                        binding.ismi.text.toString(),
                        binding.familya.text.toString(),
                        binding.phoneNumber.text.toString(),
                        a,
                        MyData.grupalar
                    )
                )
                requireActivity().supportFragmentManager.popBackStack()
            }else{
                Toast.makeText(requireContext(), "Complete the sections completely", Toast.LENGTH_SHORT).show()
            }

        }
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root


    }
}