package com.example.polestarinfo.fragments


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.polestarinfo.R
import com.example.polestarinfo.benchmark.Benchmark
import com.example.polestarinfo.constants.Constant
import com.example.polestarinfo.model.Score
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.system.measureTimeMillis

class BenchmarkTabFragment : Fragment() {
    private lateinit var idText: TextView
    private lateinit var manufacturerText: TextView
    private lateinit var sdkText: TextView
    private lateinit var boardText: TextView
    private lateinit var hardwareText: TextView
    private lateinit var benchmarkButton: Button

    private lateinit var benchmark: Job
    private lateinit var runningComputation: Job
    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_benchmark_tab, container, false)

        idText = view.findViewById(R.id.build_id)
        manufacturerText = view.findViewById(R.id.manufacturer)
        sdkText = view.findViewById(R.id.sdk)
        boardText = view.findViewById(R.id.board)
        hardwareText = view.findViewById(R.id.hardware)
        benchmarkButton = view.findViewById(R.id.benchmark_button)

        val id = "Build id: " + Build.ID
        val manufacture = "Manufacturer: " + Build.MANUFACTURER
        val sdk = "Sdk version: " + Build.VERSION.SDK
        val board = "Board: " + Build.BOARD
        val hardware = "Hardware: " + Build.HARDWARE

        idText.text = id
        manufacturerText.text = manufacture
        sdkText.text = sdk
        boardText.text = board
        hardwareText.text = hardware


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = MaterialAlertDialogBuilder(view.context, R.style.AlertDialogTheme)
            .setTitle("Benchmarking")
            .setMessage("Running....")
            .setCancelable(false)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                benchmark.cancel()
                runningComputation.cancel()
            }
            .create()

        benchmarkButton.setOnClickListener {
            benchmark = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
                dialog.show()
                dialog.window!!.setLayout(Constant.DIALOG_WIDTH, 450)
                val score = compute()
                delay(1000)
                dialog.dismiss()

                Log.d("Score: ", score.toString())
                //TODO: show results and save them into ROOM.
                //TODO: recache.
            }
        }
    }

    private suspend fun compute(): Score {
        val duration = measureTimeMillis {
            runningComputation = CoroutineScope(Dispatchers.Default).launch {
                Benchmark.primalityTest()
                Log.d("Job1", "primality test done")
                Benchmark.factorialCalculation()
                Log.d("Job2", "factorial calculation done")
                Benchmark.sorting()
                Log.d("Job3", "list sorting done")
                Benchmark.matrixMultiplication()
                Log.d("Job4", "matrix multiplication done")
            }
            runningComputation.join()
        }

        return Score(duration, DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
    }
}