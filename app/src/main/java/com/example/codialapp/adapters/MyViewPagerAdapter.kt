package com.example.codialapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.codialapp.fragments.Groups.OpenedGroupFragment
import com.example.codialapp.fragments.Groups.OpeningGroupFragment

class MyViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        if (position==0){
            return OpenedGroupFragment()
        }else return OpeningGroupFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val list = arrayOf("Opened groups", "Opening groups")
        return list[position]
    }


}