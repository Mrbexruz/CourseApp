package com.example.codialapp.fragments.mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codialapp.R
import com.example.codialapp.adapters.MentorsAdapter
import com.example.codialapp.databinding.CustomMentorBinding
import com.example.codialapp.databinding.FragmentListBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Kurslar
import com.example.codialapp.models.Mentorlar

class ListFragment : Fragment() {
    private val binding by lazy { FragmentListBinding.inflate(layoutInflater) }
    lateinit var myDb: MyDb
    lateinit var mentorsAdapter: MentorsAdapter
    lateinit var list: ArrayList<Mentorlar>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDb = MyDb(requireContext())
        val mentorId = arguments?.getSerializable("p") as Kurslar
        onResume()
        binding.kursName.text = mentorId.name
        binding.add.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext()).create()
            val customMentorBinding = CustomMentorBinding.inflate(layoutInflater)
            customMentorBinding.qoshish.setOnClickListener {
                if (customMentorBinding.name.text.isNotEmpty() &&
                    customMentorBinding.lastName.text.isNotEmpty() &&
                    customMentorBinding.number.text.isNotEmpty()
                ) {
                    val mentor = Mentorlar(
                        1,
                        customMentorBinding.name.text.toString(),
                        customMentorBinding.lastName.text.toString(),
                        customMentorBinding.number.text.toString(),
                        mentorId
                    )
                    myDb.addMentor(mentor)
                } else {
                    Toast.makeText(context, "Complete the sections completely", Toast.LENGTH_SHORT).show()
                }
                onResume()
                dialog.cancel()
            }

            customMentorBinding.yopish.setOnClickListener {
                dialog.cancel()
            }
            dialog.setView(customMentorBinding.root)
            dialog.show()
        }

        val myLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rv.apply {
            layoutManager = myLayoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), myLayoutManager.orientation))
        }
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        myDb = MyDb(requireContext())
        tartibla()
        mentorsAdapter = MentorsAdapter(object : MentorsAdapter.RvAction {
            override fun editClick(mentorlar: Mentorlar) {
                val mentorId = arguments?.getSerializable("p") as Kurslar
                val dialog = AlertDialog.Builder(requireContext()).create()
                val customMentorBinding = CustomMentorBinding.inflate(layoutInflater)
                customMentorBinding.name.setText(mentorlar.name)
                customMentorBinding.lastName.setText(mentorlar.lastName)
                customMentorBinding.number.setText(mentorlar.number)
                customMentorBinding.qoshish.text = "Edit"
                customMentorBinding.qoshish.setOnClickListener {
                    if (customMentorBinding.name.text.isNotEmpty() &&
                        customMentorBinding.lastName.text.isNotEmpty() &&
                        customMentorBinding.number.text.isNotEmpty()
                    ) {
                        val mentor = Mentorlar(
                            mentorlar.id,
                            customMentorBinding.name.text.toString(),
                            customMentorBinding.lastName.text.toString(),
                            customMentorBinding.number.text.toString(),
                            mentorId
                        )
                        myDb.editMentor(mentor)
                    } else {
                        Toast.makeText(context, "Complete the sections completely", Toast.LENGTH_SHORT).show()
                    }
                    onResume()
                    dialog.cancel()
                }
                customMentorBinding.yopish.setOnClickListener {
                    dialog.cancel()
                }
                dialog.setView(customMentorBinding.root)
                dialog.show()
            }

            override fun deleteClick(mentorlar: Mentorlar) {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Confirmation")
                alertDialog.setMessage("${mentorlar.name} ${mentorlar.lastName} do you want to delete ?")

                // Positive button: Confirm delete
                alertDialog.setPositiveButton("Ha") { _, _ ->
                    var canDelete = true
                    for (grupalar in myDb.showGroup()) {
                        if (grupalar.mentor_id?.id == mentorlar.id) {
                            canDelete = false
                            Toast.makeText(
                                context,
                                "Complete all groups of this mentor first",
                                Toast.LENGTH_SHORT
                            ).show()
                            break
                        }
                    }
                    if (canDelete) {
                        myDb.deleteMentor(mentorlar)
                        onResume()
                        Toast.makeText(context, "Mentor deleted", Toast.LENGTH_SHORT).show()
                    }
                }

                // Negative button: Cancel delete
                alertDialog.setNegativeButton("NO") { dialog, _ ->
                    dialog.dismiss()
                }

                // Show the dialog
                alertDialog.show()
            }
        }, list)
        binding.rv.adapter = mentorsAdapter
    }

    private fun tartibla() {
        val mentorId = arguments?.getSerializable("p") as Kurslar
        this.list = ArrayList()
        for (mentorlar in myDb.showMentor()) {
            if (mentorlar.kurs_id?.id == mentorId.id) {
                list.add(mentorlar)
            }
        }
    }
}
