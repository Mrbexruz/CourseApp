package com.example.codialapp.fragments.Groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codialapp.R
import com.example.codialapp.adapters.StudentAdapter
import com.example.codialapp.adapters.utils.MyData
import com.example.codialapp.databinding.FragmentStudentBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Talabalar

class StudentFragment : Fragment(), StudentAdapter.RvAction {
    private val binding by lazy { FragmentStudentBinding.inflate(layoutInflater) }
    lateinit var myDb: MyDb
    lateinit var list: ArrayList<Talabalar>
    lateinit var studentAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raqam = arguments?.getInt("raqam")
        val grupa_id = arguments?.getInt("key10")
        myDb = MyDb(requireContext())

        var name = ""
        var vaqt = ""
        var groupFound = false
        myDb.showGroup().forEach {
            if (it.id == grupa_id) {
                vaqt = it.time.toString()
                name = it.name.toString()
                MyData.grupalar = it
                groupFound = true
            }
        }

        if (!groupFound) {
            Toast.makeText(requireContext(), "Group not found", Toast.LENGTH_SHORT).show()
            return binding.root
        }

        binding.nameGroup.text = name
        binding.vaqti.text = vaqt
        binding.teacher.text = MyData.grupalar?.mentor_id?.name
        binding.teacherLn.text = MyData.grupalar?.mentor_id?.lastName

        val myLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rv.apply {
            layoutManager = myLayoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), myLayoutManager.orientation))
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.addStudentFragment)
        }

        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        if (raqam == 100) {
            binding.btnBoshlash.visibility = View.GONE
        } else {
            binding.btnBoshlash.setOnClickListener {
                val p = MyData.grupalar
                val grupalar = Grupalar(
                    p?.id,
                    p?.name,
                    p?.mentor_id,
                    p?.day,
                    p?.time,
                    1
                )
                myDb.editGroup(grupalar)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val grupa_id = arguments?.getInt("key10")
        myDb = MyDb(requireContext())
        list = ArrayList()

        for (student in myDb.showStudents()) {
            if (student.groupId?.id == grupa_id) {
                list.add(student)
            }
        }

        studentAdapter = StudentAdapter(this@StudentFragment, list)
        binding.rv.adapter = studentAdapter
        binding.count.text = list.size.toString()
    }

    override fun editClick(talabalar: Talabalar) {
        findNavController().navigate(R.id.editStudentFragment)
        MyData.talabalar = talabalar
    }

    override fun deleteClick(talabalar: Talabalar) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Confirmation")
        alertDialog.setMessage("${talabalar.firstName} do you want to delete ?")

        // Positive button: Confirm delete
        alertDialog.setPositiveButton("Ha") { _, _ ->
            myDb.deleteStudent(talabalar)
            list.remove(talabalar) // Remove the student from the list
            studentAdapter.notifyDataSetChanged() // Update the adapter
            Toast.makeText(context, "${talabalar.firstName} deleted", Toast.LENGTH_SHORT).show()
        }

        // Negative button: Cancel delete
        alertDialog.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        // Show the dialog
        alertDialog.show()
    }
}
