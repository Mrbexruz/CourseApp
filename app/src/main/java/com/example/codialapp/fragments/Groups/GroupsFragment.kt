package com.example.codialapp.fragments.Groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialapp.R
import com.example.codialapp.adapters.CoursesAdapter
import com.example.codialapp.adapters.utils.MyData
import com.example.codialapp.databinding.FragmentGroupsBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Kurslar

class GroupsFragment : Fragment() {
    private val binding by lazy { FragmentGroupsBinding.inflate(layoutInflater) }
    lateinit var coursesAdapter: CoursesAdapter
    lateinit var myDb: MyDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDb = MyDb(requireContext())
        val list = myDb.showKurs()
        coursesAdapter = CoursesAdapter(requireContext(),object :CoursesAdapter.RvAction{
            override fun onClick(position: Int, kurslar: Kurslar) {
                for (mentorlar in myDb.showMentor()) {
                    if (mentorlar.kurs_id?.id==kurslar.id){
                        findNavController().navigate(R.id.addGroupFragment3)
                        MyData.kurslar = kurslar
                        break
                    }else{
                        Toast.makeText(context, "There is no mentor in this direction yet", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        },list)

        binding.rv.adapter = coursesAdapter
        binding.chiqish.setOnClickListener {

        }
        return binding.root

    }
}