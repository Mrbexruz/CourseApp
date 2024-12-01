package com.example.codialapp.fragments.Courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codialapp.R
import com.example.codialapp.databinding.FragmentDataBinding
import com.example.codialapp.db.MyDb

class DataFragment : Fragment() {
    private val binding by lazy { FragmentDataBinding.inflate(layoutInflater) }
    lateinit var myDb: MyDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var id = arguments.let { it!!.getInt("position") }
        myDb = MyDb(requireContext())
        binding.name.text = myDb.showKurs()[id].name
        binding.about.text = myDb.showKurs()[id].about

        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root

    }

}