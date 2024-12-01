package com.example.codialapp.fragments.Groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialapp.R
import com.example.codialapp.adapters.GroupsAdapter
import com.example.codialapp.adapters.utils.MyData
import com.example.codialapp.databinding.FragmentOpeningGroupBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Talabalar
import kotlin.collections.ArrayList

class OpeningGroupFragment : Fragment() {
    private val binding by lazy { FragmentOpeningGroupBinding.inflate(layoutInflater) }
    private lateinit var groupsAdapter: GroupsAdapter
    private lateinit var myDb: MyDb
    private lateinit var list: ArrayList<Grupalar>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        myDb = MyDb(requireContext())
        list = ArrayList()

        // Fetch unopened groups
        for (grupalar in myDb.showGroup()) {
            if (grupalar.mentor_id?.kurs_id?.id == MyData.kurslar?.id && grupalar.ochilganmi == 0) {
                list.add(grupalar)
            }
        }

        // Initialize adapter with delete confirmation
        groupsAdapter = GroupsAdapter(object : GroupsAdapter.RvAction5 {
            override fun viewClick(grupalar: Grupalar, position: Int) {
                findNavController().navigate(R.id.studentFragment, bundleOf("key10" to grupalar.id))
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
                    alertDialog.setTitle("Confirm")
                    alertDialog.setMessage(
                        "Do you want to delete all students and group in this group?"
                    )

                    // Confirm deletion of group and students
                    alertDialog.setPositiveButton("Yes") { _, _ ->
                        listStudents.forEach { myDb.deleteStudent(it) }
                        myDb.deleteGroup(grupalar)
                        list.remove(grupalar) // Remove from list
                        groupsAdapter.notifyItemRemoved(position)
                        Toast.makeText(context, "Group and students deleted", Toast.LENGTH_SHORT).show()
                    }

                    // Cancel deletion
                    alertDialog.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }

                    alertDialog.show()
                } else {
                    val alertDialog = AlertDialog.Builder(requireContext())
                    alertDialog.setTitle("Confirmation")
                    alertDialog.setMessage("Do you want to delete the group?")

                    // Confirm group deletion
                    alertDialog.setPositiveButton("Ha") { _, _ ->
                        myDb.deleteGroup(grupalar)
                        list.remove(grupalar) // Remove from list
                        groupsAdapter.notifyItemRemoved(position)
                        Toast.makeText(context, "Group deleted", Toast.LENGTH_SHORT).show()
                    }

                    // Cancel deletion
                    alertDialog.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }

                    alertDialog.show()
                }
            }
        }, list, myDb)

        binding.rvOchilayotgan.adapter = groupsAdapter
    }
}
