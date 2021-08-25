package com.example.polestarinfo

import android.car.Car
import android.car.hardware.property.CarPropertyManager
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.polestarinfo.constants.Constant.permissions
import com.example.polestarinfo.fragments.CarInfoFragment
import com.example.polestarinfo.fragments.FuelFragment
import com.example.polestarinfo.fragments.SpeedFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var car : Car
    private lateinit var mCarPropertyManager: CarPropertyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCar()
        initBottomNavigation()
        replaceFragment(SpeedFragment())
    }

    override fun onResume() {
        super.onResume()

        if(checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(permissions[1]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(permissions[2]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(permissions[3]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(permissions[4]) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(permissions[5]) == PackageManager.PERMISSION_GRANTED
        ) {
            initCar()
        } else {
            requestPermissions(permissions, 0)
        }
    }

    override fun onPause() {
        if(car.isConnected) {
            car.disconnect()
        }

        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions[0] == Car.PERMISSION_SPEED && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
            permissions[1] == Car.PERMISSION_ENERGY && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
            permissions[2] == Car.PERMISSION_POWERTRAIN && grantResults[2] == PackageManager.PERMISSION_GRANTED &&
            permissions[3] == Car.PERMISSION_CAR_INFO && grantResults[3] == PackageManager.PERMISSION_GRANTED &&
            permissions[4] == Car.PERMISSION_EXTERIOR_ENVIRONMENT && grantResults[4] == PackageManager.PERMISSION_GRANTED &&
            permissions[5] == Car.PERMISSION_ENERGY_PORTS && grantResults[5] == PackageManager.PERMISSION_GRANTED

        ) {
            initCar()
        }
    }

    private fun initCar() {
        car = Car.createCar(this)
        mCarPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
    }

    private fun initBottomNavigation(){
        bottomNavigation.setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.page_speed -> {
                    replaceFragment(SpeedFragment())
                    true
                }
                R.id.page_fuel -> {
                    replaceFragment(FuelFragment())
                    true
                }
                R.id.page_carinfo -> {
                    replaceFragment(CarInfoFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack:Boolean = false){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        when(addToBackStack){
            true -> {
                transaction.addToBackStack(null)
            }
        }
        transaction.commit()
    }

    fun getCar() = car
    fun getCarPropertyManager() = mCarPropertyManager
}