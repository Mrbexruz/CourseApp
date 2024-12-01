package com.example.codialapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.codialapp.R
import com.example.codialapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.kurslar.setOnClickListener {

            findNavController().navigate(R.id.coursesFragment2)
        }
        binding.mentorlar.setOnClickListener {
            findNavController().navigate(R.id.mentorsFragment)
        }
        binding.guruhlar.setOnClickListener {
            findNavController().navigate(R.id.groupsFragment)
        }

        return binding.root

    }
}