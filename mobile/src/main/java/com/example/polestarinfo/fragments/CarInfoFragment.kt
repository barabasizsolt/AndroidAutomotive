package com.example.polestarinfo.fragments

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.polestarinfo.R
import androidx.core.content.ContextCompat.getSystemService

import android.hardware.SensorManager
import android.util.Log
import androidx.core.content.ContextCompat


class CarInfoFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_info, container, false)

        val sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        Log.d("Sensors", deviceSensors.toString())

        return view
    }
}