package com.example.polestarinfo.fragments

import android.content.Context
import android.hardware.Sensor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.polestarinfo.R
import android.hardware.SensorManager
import android.util.Log
import com.google.android.material.tabs.TabLayout




class CarInfoFragment : Fragment() {
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        Log.d("Sensors", deviceSensors.toString())

        return view
    }
}