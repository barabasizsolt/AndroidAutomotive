package com.example.polestarinfo.fragments

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.polestarinfo.activities.MainActivity
import com.example.polestarinfo.R
import com.example.polestarinfo.constants.Constant.fuelDoorLocations
import com.example.polestarinfo.constants.Constant.seatLocations

class CarInfoTabFragment : Fragment() {
    private lateinit var mCarPropertyManager: CarPropertyManager
    private lateinit var makeText: TextView
    private lateinit var modelText: TextView
    private lateinit var modelYearText: TextView
    private lateinit var driverSeatLocationText: TextView
    private lateinit var fuelDoorLocationText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_info_tab, container, false)

        makeText = view.findViewById(R.id.make)
        modelText = view.findViewById(R.id.model)
        modelYearText = view.findViewById(R.id.model_year)
        driverSeatLocationText = view.findViewById(R.id.driver_seat_location)
        fuelDoorLocationText = view.findViewById(R.id.fuel_door_location)

        mCarPropertyManager = (activity as MainActivity).getCarPropertyManager()
        val make = "Make: " + mCarPropertyManager.getProperty<String>(VehiclePropertyIds.INFO_MAKE, 0).value
        val model = "Model: " + mCarPropertyManager.getProperty<String>(VehiclePropertyIds.INFO_MODEL, 0).value
        val modelYear = "Model year: " + mCarPropertyManager.getIntProperty(VehiclePropertyIds.INFO_MODEL_YEAR, 0).toString()
        val driverSeatLocation = "Driver seat location: " + seatLocations[mCarPropertyManager.getIntProperty(VehiclePropertyIds.INFO_DRIVER_SEAT, 0)]
        val fuelDoorLocation = "Fuel door location: " + fuelDoorLocations[mCarPropertyManager.getIntProperty(VehiclePropertyIds.INFO_EV_PORT_LOCATION, 0)]

        makeText.text = make
        modelText.text = model
        modelYearText.text = modelYear
        driverSeatLocationText.text = driverSeatLocation
        fuelDoorLocationText.text = fuelDoorLocation

        return view
    }
}