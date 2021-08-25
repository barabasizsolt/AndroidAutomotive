package com.example.polestarinfo.fragments

import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.polestarinfo.MainActivity
import com.example.polestarinfo.R
import com.example.polestarinfo.constants.Constant
import me.ibrahimsn.lib.Speedometer
import kotlin.math.roundToInt

class SpeedFragment : Fragment() {
    private lateinit var mCarPropertyManager:CarPropertyManager
    private lateinit var speedometer: Speedometer
    private lateinit var gear: TextView
    private lateinit var parkingBrake: ImageView
    private lateinit var lowFuel: ImageView

    private var prevSpeed:Float = 0.0F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_speed, container, false)

        mCarPropertyManager = (activity as MainActivity).getCarPropertyManager()
        prevSpeed = mCarPropertyManager.getFloatProperty(VehiclePropertyIds.PERF_VEHICLE_SPEED, 0)
        speedometer = view.findViewById(R.id.speedometer)
        gear = view.findViewById(R.id.gear)
        // TODO: fix better
        gear.text = Constant.gearList[ mCarPropertyManager.getIntProperty(VehiclePropertyIds.GEAR_SELECTION, 0)]
        parkingBrake = view.findViewById(R.id.parking_brake)
        lowFuel = view.findViewById(R.id.low_fuel)
        registerCarPropertyCallback()

        return view
    }

    override fun onResume() {
        super.onResume()

        registerCarPropertyCallback()
    }

    override fun onPause() {
        super.onPause()

        unregisterCarPropertyCallback()
    }

    private fun registerCarPropertyCallback() {
        mCarPropertyManager.registerCallback(
            speedCallback,
            VehiclePropertyIds.PERF_VEHICLE_SPEED,
            CarPropertyManager.SENSOR_RATE_FASTEST
        )
        mCarPropertyManager.registerCallback(
            gearCallback,
            VehiclePropertyIds.GEAR_SELECTION,
            CarPropertyManager.SENSOR_RATE_FASTEST
        )
        mCarPropertyManager.registerCallback(
            parkingBrakeCallback,
            VehiclePropertyIds.PARKING_BRAKE_ON,
            CarPropertyManager.SENSOR_RATE_FASTEST
        )
        mCarPropertyManager.registerCallback(
            lowFuelCallback,
            VehiclePropertyIds.FUEL_LEVEL_LOW,
            CarPropertyManager.SENSOR_RATE_FASTEST
        )
    }

    private fun unregisterCarPropertyCallback() {
        mCarPropertyManager.unregisterCallback(speedCallback)
        mCarPropertyManager.unregisterCallback(gearCallback)
        mCarPropertyManager.unregisterCallback(parkingBrakeCallback)
        mCarPropertyManager.unregisterCallback(lowFuelCallback)
    }

    private val speedCallback = object :  CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            val currentSpeed = ((carPropertyValue.value as Float) * Constant.KM_MULTIPLIER)

            if(prevSpeed != currentSpeed) {
                speedometer.setSpeed(currentSpeed.roundToInt(), 0L)
                prevSpeed = currentSpeed
            }
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(ContentValues.TAG, "CarPropertyManager.onSpeedChangedError")
        }
    }

    private val gearCallback = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            val currentGearId = carPropertyValue.value
            gear.text = Constant.gearList[currentGearId]
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(ContentValues.TAG, "CarPropertyManager.onGearChangedError")
        }
    }

    private val parkingBrakeCallback = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            when(carPropertyValue.value as Boolean){
                true -> parkingBrake.visibility = View.VISIBLE
                else -> parkingBrake.visibility = View.INVISIBLE
            }
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(ContentValues.TAG, "CarPropertyManager.onParkingBrakeChangedError")
        }
    }

    private val lowFuelCallback = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            when(carPropertyValue.value as Boolean){
                true -> lowFuel.visibility = View.VISIBLE
                else -> lowFuel.visibility = View.INVISIBLE
            }
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(ContentValues.TAG, "CarPropertyManager.onParkingBrakeChangedError")
        }
    }
}