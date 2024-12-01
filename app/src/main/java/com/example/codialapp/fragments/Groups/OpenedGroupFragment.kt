package com.example.codialapp.fragments.Groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codialapp.R
import com.example.codialapp.adapters.GroupsAdapter
import com.example.codialapp.adapters.utils.MyData
import com.example.codialapp.databinding.FragmentOpenedGroupBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Talabalar

class OpenedGroupFragment : Fragment() {
    private val binding by lazy { FragmentOpenedGroupBinding.inflate(layoutInflater) }
    private val myDb by lazy { MyDb(requireContext()) }
    lateinit var groupsAdapter: GroupsAdapter
    private var list: ArrayList<Grupalar> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        list.clear()
        for (grupalar in myDb.showGroup()) {
            if (grupalar.mentor_id?.kurs_id?.id == MyData.kurslar?.id && grupalar.ochilganmi == 1) {
                list.add(grupalar)
            }
        }

        groupsAdapter = GroupsAdapter(object : GroupsAdapter.RvAction5 {
            override fun viewClick(grupalar: Grupalar, position: Int) {
                findNavController().navigate(
                    R.id.studentFragment,
                    bundleOf("raqam" to 100, "key10" to grupalar.id)
                )
            }

            override fun editClick(grupalar: Grupalar, position: Int) {
                findNavController().navigate(
                    R.id.editGroupFragment2,
                    bundleOf("edit" to grupalar.id)
                )
            }

            override fun deleteClick(grupalar: Grupalar, position: Int) {
                val listStudents = ArrayList<Talabalar>()
                myDb.showStudents().forEach {
                    if (it.groupId?.id == grupalar.id) {
                        listStudents.add(it)
                    }
                }

                if (listStudents.isNotEmpty()) {
                    val alertDialog = AlertDialog.Builder(requireContext())
                    alertDialog.setTitle("Confirmation")
                    alertDialog.setMessage(
                        "Do you want to delete all students and group in this group?"
                    )

                    // Positive button: Confirm delete
                    alertDialog.setPositiveButton("Ha") { _, _ ->
                        listStudents.forEach { myDb.deleteStudent(it) }
                        myDb.deleteGroup(grupalar)
                        list.remove(grupalar) // Remove group from the list
                        groupsAdapter.notifyItemRemoved(position)
                        Toast.makeText(context, "Group and students deleted", Toast.LENGTH_SHORT)
                            .show()
                    }

                    // Negative button: Cancel delete
                    alertDialog.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }

                    // Show the dialog
                    alertDialog.show()
                } else {
                    val alertDialog = AlertDialog.Builder(requireContext())
                    alertDialog.setTitle("Confirmation")
                    alertDialog.setMessage("Do you want to delete the group?")

                    alertDialog.setPositiveButton("Ha") { _, _ ->
                        myDb.deleteGroup(grupalar)
                        list.remove(grupalar) // Remove group from the list
                        groupsAdapter.notifyItemRemoved(position)
                        Toast.makeText(context, "Group deleted", Toast.LENGTH_SHORT).show()
                    }

                    alertDialog.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }

                    alertDialog.show()
                }
            }
        }, list, myDb)

        binding.rvOchilgan.adapter = groupsAdapter
    }
}
