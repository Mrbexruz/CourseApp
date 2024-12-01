package com.example.codialapp.fragments.Groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.codialapp.R
import com.example.codialapp.adapters.MyViewPagerAdapter
import com.example.codialapp.adapters.utils.MyData
import com.example.codialapp.databinding.FragmentAddGroupBinding
import com.example.codialapp.db.MyDb


class AddGroupFragment : Fragment() {
    private val bining by lazy { FragmentAddGroupBinding.inflate(layoutInflater) }
    lateinit var myViewPagerAdapter : MyViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val p = MyData.kurslar
        myViewPagerAdapter = MyViewPagerAdapter(childFragmentManager)

        bining.pager.adapter = myViewPagerAdapter


        bining.tablayout.setupWithViewPager(bining.pager)

        val myDb = MyDb(requireContext())

        bining.name.text = p?.name

        bining.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position==0 ) bining.add.visibility = View.INVISIBLE else bining.add.visibility = View.VISIBLE
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        bining.add.setOnClickListener {
            findNavController().navigate(R.id.groupAddFragment2, bundleOf("alisher" to p ))
        }
        bining.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return bining.root
    }
}