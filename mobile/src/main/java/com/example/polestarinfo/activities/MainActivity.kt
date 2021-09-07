package com.example.polestarinfo.activities

import android.car.Car
import android.car.hardware.property.CarPropertyManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.polestarinfo.R
import com.example.polestarinfo.databases.ScoreViewModel
import com.example.polestarinfo.fragments.BenchmarkFragment
import com.example.polestarinfo.fragments.CarInfoFragment
import com.example.polestarinfo.fragments.FuelFragment
import com.example.polestarinfo.fragments.SpeedFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var car : Car
    private lateinit var mCarPropertyManager: CarPropertyManager

    lateinit var benchmarkScoreViewModel: ScoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        benchmarkScoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)

        initCar()
        initBottomNavigation()
        replaceFragment(SpeedFragment(), R.id.fragment_container)
    }

    override fun onPause() {
        if(car.isConnected) {
            car.disconnect()
        }

        super.onPause()
    }

    private fun initCar() {
        car = Car.createCar(this)
        mCarPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
    }

    private fun initBottomNavigation(){
        bottomNavigation.setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.page_speed -> {
                    replaceFragment(SpeedFragment(), R.id.fragment_container)
                    true
                }
                R.id.page_fuel -> {
                    replaceFragment(FuelFragment(), R.id.fragment_container)
                    true
                }
                R.id.page_carinfo -> {
                    replaceFragment(CarInfoFragment(), R.id.fragment_container)
                    true
                }
                R.id.page_benchmark -> {
                    replaceFragment(BenchmarkFragment(), R.id.fragment_container)
                    true
                }
                else -> false
            }
        }
    }

    fun replaceFragment(fragment: Fragment, containerId: Int, addToBackStack:Boolean = false, withAnimation:Boolean = true){
        val transaction = supportFragmentManager.beginTransaction()
        when(withAnimation){
            true -> {
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            }
        }
        transaction.replace(containerId, fragment)
        when(addToBackStack){
            true -> {
                transaction.addToBackStack(null)
            }
        }
        transaction.commit()
    }

    fun getCar() = car
    fun getCarPropertyManager() = mCarPropertyManager
    fun getDatabase() = benchmarkScoreViewModel
}