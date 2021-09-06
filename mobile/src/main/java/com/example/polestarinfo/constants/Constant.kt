package com.example.polestarinfo.constants

import android.car.Car
import android.content.Context
import android.hardware.Sensor
import com.example.polestarinfo.R
import com.example.polestarinfo.model.Score
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Constant {
    val permissions = arrayOf(
        Car.PERMISSION_SPEED,
        Car.PERMISSION_ENERGY,
        Car.PERMISSION_POWERTRAIN,
        Car.PERMISSION_CAR_INFO,
        Car.PERMISSION_EXTERIOR_ENVIRONMENT,
        Car.PERMISSION_ENERGY_PORTS
    )

    val gearList = mapOf(
        1 to "N (Neutral)",
        2 to "R (Reverse)",
        4 to "P (Park)",
        8 to "D (Drive)",
        16 to "First Gear",
        64 to "Third Gear",
        128 to "Fourth Gear",
        256 to "Fifth Gear",
        512 to "Sixth Gear",
        1024 to "Seventh Gear",
        2048 to "Eighth Gear",
        4096 to "Ninth Gear"
    )

    val fuelList = mapOf(
        0 to "Unknown",
        1 to "Unleaded",
        2 to "Leaded gasoline",
        3 to "1 Grade Diesel",
        4 to "2 Grade Diesel",
        5 to "Biodiesel",
        6 to "E85",
        7 to "LPG",
        8 to "CNG",
        9 to "LNG",
        10 to "Electric",
        11 to "Hydrogen",
        12 to "Other"
    )

    val seatLocations = mapOf(
        0 to "Seat unknown",
        1 to "Row 1 left side seat",
        2 to "Row 1 center seat",
        4 to "Row 1 right side seat",
        16 to "Row 2 left side seat",
        32 to "Row 2 center seat",
        64 to "Row 2 right side seat",
        256 to "Row 3 left side seat",
        1024 to "Row 3 right side seat"
    )

    val fuelDoorLocations = mapOf(
        1 to "Front Left",
        2 to "Front right",
        3 to "Rear right",
        4 to "Rear left",
        5 to "Front",
        6 to "Rear"
    )

    const val KM_MULTIPLIER = 3.59999987F
    const val MAX_WH = 150000.000F
    const val WH_TO_KWH = 1000
    const val M_TO_MI = 1609.344F
    const val MW_TO_KW = 1000
    const val ODOMETER = "Odometer"
    const val CHARGE = "Charge rate"
    const val ODOMETER_DEFAULT_VAL = "5000 km"
    private const val SENSOR_DIALOG_WIDTH = 900
    private const val SENSOR_DIALOG_HEIGHT = 820
    const val BENCHMARK_DIALOG_WIDTH = 900
    const val BENCHMARK_DIALOG_HEIGHT = 600
    private const val BENCHMARK_RESULT_DIALOG_WIDTH = 900
    private const val BENCHMARK_RESULT_DIALOG_HEIGHT = 700
    const val DELETE_DIALOG_WIDTH = 900
    const val DELETE_DIALOG_HEIGHT = 350
    const val JOB1_MESSAGE = "Running primality test..."
    const val JOB2_MESSAGE = "Running factorial calculation..."
    const val JOB3_MESSAGE = "Running list sorting..."
    const val JOB4_MESSAGE = "Running matrix multiplication..."
    const val BATTERY_LOW_PERCENTAGE = 20
    const val BATTERY_FULL_PERCENTAGE = 100

    fun showResultDialog(score: Score, context: Context){
        val totalScore = "Total time: " + score.score + " ms"
        val benchmarkTime = score.name
        val primalityScore = "Primality test time: " + score.primalityScore + " ms"
        val factorialScore = "Factorial calculation time: " + score.factorialScore + " ms"
        val sortingScore = "List sorting time: " + score.sortingScore + " ms"
        val matrixScore = "Matrix multiplication time: " + score.matrixScore + " ms"

        val items = arrayOf(benchmarkTime, totalScore, primalityScore, factorialScore, sortingScore, matrixScore)
        MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
            .setTitle(R.string.benchmark_result_dialog_title)
            .setItems(items) { _, _ -> }
            .setPositiveButton(R.string.benchmark_result_dialog_positive_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
            .window!!.setLayout(BENCHMARK_RESULT_DIALOG_WIDTH, BENCHMARK_RESULT_DIALOG_HEIGHT)
    }

    fun showSensorDetailsDialog(sensor: Sensor, context: Context){
        val name = sensor.name
        val vendor = "Vendor: " + sensor.vendor
        val version = "Version:" + sensor.version
        val type = "Type: " + sensor.type
        val maxRange = "Maximum range: " + sensor.maximumRange
        val res = "Resolution: " + sensor.resolution
        val power = "Power: " + sensor.power
        val minDelay = "Minimum delay: " + sensor.minDelay

        val items = arrayOf(name, vendor, version, type, maxRange, res, power, minDelay)
        MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
            .setTitle(R.string.sensor_title)
            .setItems(items) { _, _ -> }
            .setPositiveButton(R.string.benchmark_result_dialog_positive_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
            .window!!.setLayout(Constant.SENSOR_DIALOG_WIDTH, Constant.SENSOR_DIALOG_HEIGHT)
    }
}