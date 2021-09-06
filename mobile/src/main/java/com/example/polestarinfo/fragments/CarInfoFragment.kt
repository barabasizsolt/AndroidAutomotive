package com.example.polestarinfo.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.polestarinfo.activities.MainActivity
import com.example.polestarinfo.R
import com.google.android.material.tabs.TabLayout

class CarInfoFragment : Fragment() {
    private lateinit var tabLayout: TabLayout

    override fun onResume() {
        super.onResume()
        tabLayout.getTabAt(1)!!.select()
        tabLayout.getTabAt(0)!!.select()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_info, container, false)

        tabLayout = view.findViewById(R.id.tab_layout)
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.setCustomView(R.layout.custom_tab_layout)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 -> (activity as MainActivity).replaceFragment(CarInfoTabFragment(), R.id.car_fragment_container)
                    1 -> (activity as MainActivity).replaceFragment(CarSensorsTabFragment(), R.id.car_fragment_container)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })

        return view
    }
}