package com.example.codialapp.fragments.mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialapp.R
import com.example.codialapp.adapters.CoursesAdapter
import com.example.codialapp.databinding.FragmentMentorsBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Kurslar


class MentorsFragment : Fragment() {
    lateinit var coursesAdapter: CoursesAdapter
 private val binding by lazy { FragmentMentorsBinding.inflate(layoutInflater) }
    lateinit var myDb: MyDb
    lateinit var kurslar: Kurslar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDb = MyDb(requireContext())
        val list = myDb.showKurs()
        coursesAdapter = CoursesAdapter(requireContext(), object : CoursesAdapter.RvAction{
            override fun onClick(position: Int, kurslar: Kurslar) {
                findNavController().navigate(R.id.listFragment, bundleOf("p" to kurslar))
            }

        }, list)
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.rv.adapter = coursesAdapter
        return binding.root
    }

}