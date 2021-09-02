package com.example.polestarinfo.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polestarinfo.R
import com.example.polestarinfo.adapters.SensorAdapter
import com.example.polestarinfo.constants.Constant
import com.example.polestarinfo.interfaces.OnItemClickListener

class CarSensorsTabFragment : Fragment(), OnItemClickListener {
    private lateinit var deviceSensors: List<Sensor>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_sensors_tab, container, false)

        val sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SensorAdapter(deviceSensors, this)
        recyclerview.adapter = adapter

        return view
    }

    override fun onItemClick(position: Int) {
        Constant.showSensorDetailsDialog(deviceSensors[position], requireContext())
    }
}