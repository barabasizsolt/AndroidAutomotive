package com.example.polestarinfo.fragments

import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.polestarinfo.activities.MainActivity
import com.example.polestarinfo.R
import com.example.polestarinfo.constants.Constant
import com.example.polestarinfo.constants.Constant.CHARGE
import com.example.polestarinfo.constants.Constant.MW_TO_KW
import com.example.polestarinfo.constants.Constant.M_TO_MI
import com.example.polestarinfo.constants.Constant.ODOMETER
import com.example.polestarinfo.constants.Constant.ODOMETER_DEFAULT_VAL
import com.example.polestarinfo.constants.Constant.WH_TO_KWH
import kotlin.math.roundToInt

class FuelFragment : Fragment() {
    private lateinit var mCarPropertyManager: CarPropertyManager
    private lateinit var polestar: ImageView
    private lateinit var batteryIcon: ImageView
    private lateinit var batteryPercentage: TextView
    private lateinit var batteryCapacity: TextView
    private lateinit var currentRange: TextView
    private lateinit var odometerAndCharge: TextView
    private lateinit var odometerAndChargeHeader: TextView
    private lateinit var temperature: TextView

    private var wasCharging: Boolean = false
    private var isCharging: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fuel, container, false)

        mCarPropertyManager = (activity as MainActivity).getCarPropertyManager()
        polestar = view.findViewById(R.id.polestar2)
        batteryIcon = view.findViewById(R.id.battery_icon)
        batteryPercentage = view.findViewById(R.id.battery_level)
        batteryCapacity = view.findViewById(R.id.battery_capacity)
        currentRange = view.findViewById(R.id.range)
        odometerAndCharge = view.findViewById(R.id.odometer_and_charge)
        odometerAndCharge = view.findViewById(R.id.odometer_and_charge)
        odometerAndChargeHeader = view.findViewById(R.id.odometer_and_charge_header)
        temperature = view.findViewById(R.id.outdoor_temperature)

        wasCharging = mCarPropertyManager.getBooleanProperty(VehiclePropertyIds.EV_CHARGE_PORT_CONNECTED, 0)

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
            batteryCallback,
            VehiclePropertyIds.EV_BATTERY_LEVEL,
            CarPropertyManager.SENSOR_RATE_FASTEST
        )
        mCarPropertyManager.registerCallback(
            batteryCapacityCallback,
            VehiclePropertyIds.INFO_EV_BATTERY_CAPACITY,
            CarPropertyManager.SENSOR_RATE_NORMAL
        )
        mCarPropertyManager.registerCallback(
            rangeCallback,
            VehiclePropertyIds.RANGE_REMAINING,
            CarPropertyManager.SENSOR_RATE_FASTEST
        )
        mCarPropertyManager.registerCallback(
            chargeRateCallback,
            VehiclePropertyIds.EV_BATTERY_INSTANTANEOUS_CHARGE_RATE,
            CarPropertyManager.SENSOR_RATE_FASTEST
        )
        mCarPropertyManager.registerCallback(
            temperatureCallback,
            VehiclePropertyIds.ENV_OUTSIDE_TEMPERATURE,
            CarPropertyManager.SENSOR_RATE_FASTEST
        )
        mCarPropertyManager.registerCallback(
            energyPortConnectedCallback,
            VehiclePropertyIds.EV_CHARGE_PORT_CONNECTED,
            CarPropertyManager.SENSOR_RATE_FASTEST
        )
    }

    private fun unregisterCarPropertyCallback() {
        mCarPropertyManager.unregisterCallback(batteryCallback)
        mCarPropertyManager.unregisterCallback(batteryCapacityCallback)
        mCarPropertyManager.unregisterCallback(rangeCallback)
        mCarPropertyManager.unregisterCallback(chargeRateCallback)
        mCarPropertyManager.unregisterCallback(temperatureCallback)
        mCarPropertyManager.unregisterCallback(energyPortConnectedCallback)
    }

    private fun calculateBatteryPercentage(value: Float) = (value * 100) / Constant.MAX_WH

    private fun isCharging(value: Boolean){
        when(value){
            true -> {
                isCharging = true
                wasCharging = true

                polestar.setImageResource(R.drawable.polestar2_top_view_charching)
                batteryIcon.setImageResource(R.drawable.ic_baseline_battery_charging_24)
                odometerAndChargeHeader.text = CHARGE
                val currentCharge = mCarPropertyManager.getFloatProperty(VehiclePropertyIds.EV_BATTERY_INSTANTANEOUS_CHARGE_RATE, 0)
                val charge = (currentCharge / MW_TO_KW).roundToInt().toString() + " kW"
                odometerAndCharge.text = charge
                Toast.makeText(requireContext(), "Charging!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                isCharging = false

                polestar.setImageResource(R.drawable.polestar2_top_view)
                batteryIcon.setImageResource(R.drawable.ic_baseline_battery_full_24)
                odometerAndChargeHeader.text = ODOMETER
                // NOTE: Odometer is not available.
                odometerAndCharge.text = ODOMETER_DEFAULT_VAL
                if(wasCharging) {
                    Toast.makeText(requireContext(), "Charging finished!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private val batteryCallback = object :  CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            val batteryLevel = calculateBatteryPercentage(carPropertyValue.value as Float)
            val batteryLevelText = batteryLevel.roundToInt().toString() + " %"
            batteryPercentage.text = batteryLevelText
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(TAG, "CarPropertyManager.onBatteryChangedError")
        }
    }

    private val batteryCapacityCallback = object :  CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            val batteryCapacityText = ((carPropertyValue.value as Float) / WH_TO_KWH).roundToInt().toString() + " kWh"
            batteryCapacity.text = batteryCapacityText
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(TAG, "CarPropertyManager.onBatteryCapacityChangedError")
        }
    }

    private val rangeCallback = object :  CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            val rangeText = ((carPropertyValue.value as Float) / M_TO_MI).roundToInt().toString() + " mi"
            currentRange.text = rangeText
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(TAG, "CarPropertyManager.onRangeChangedError")
        }
    }

    private val chargeRateCallback = object :  CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            if(isCharging) {
                val chargeRateText =
                    ((carPropertyValue.value as Float) / MW_TO_KW).roundToInt().toString() + " kW"
                odometerAndCharge.text = chargeRateText
            }
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(TAG, "CarPropertyManager.onChargeRateChangedError")
        }
    }

    //NOTE: Odometer is missing, can not be accessed in Car API.

    private val temperatureCallback = object :  CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            val temperatureText = (carPropertyValue.value as Float).roundToInt().toString() + "°C"
            temperature.text = temperatureText
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(TAG, "CarPropertyManager.onTemperatureChangedError")
        }
    }

    private val energyPortConnectedCallback = object :  CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
            isCharging(carPropertyValue.value as Boolean)
        }

        override fun onErrorEvent(i: Int, i1: Int) {
            Log.e(TAG, "CarPropertyManager.onEnergyPortConnectedError")
        }
    }
}