package com.example.polestarinfo.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.polestarinfo.activities.MainActivity
import com.example.polestarinfo.R
import com.example.polestarinfo.benchmarks.Benchmark
import com.example.polestarinfo.cache.Cache
import com.example.polestarinfo.constants.Constant
import com.example.polestarinfo.models.Score
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
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
    private lateinit var progressMessage: Job
    private lateinit var benchmarkDialog: AlertDialog

    private lateinit var customDialogView: View
    private lateinit var progressBarInfo: TextView

    @SuppressLint("InflateParams")
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
        val sdk = "Sdk version: " + Build.VERSION.SDK_INT.toString()
        val board = "Board: " + Build.BOARD
        val hardware = "Hardware: " + Build.HARDWARE

        idText.text = id
        manufacturerText.text = manufacture
        sdkText.text = sdk
        boardText.text = board
        hardwareText.text = hardware

        customDialogView = inflater.inflate(R.layout.benchmark_custom_dialog_layout, null, false)
        progressBarInfo = customDialogView.findViewById(R.id.progress_bar_info)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        benchmarkDialog = MaterialAlertDialogBuilder(view.context, R.style.AlertDialogTheme)
            .setView(customDialogView)
            .setTitle(R.string.benchmark_dialog_title)
            .setCancelable(false)
            .setPositiveButton(R.string.benchmark_dialog_positive_button) { dialog, _ ->
                dialog.dismiss()
                benchmark.cancel()
                runningComputation.cancel()
                progressMessage.cancel()
            }
            .create()

        benchmarkButton.setOnClickListener {
            benchmark = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
                benchmarkDialog.show()
                benchmarkDialog.window!!.setLayout(Constant.BENCHMARK_DIALOG_WIDTH, Constant.BENCHMARK_DIALOG_HEIGHT)
                val score = compute()
                delay(1000)
                benchmarkDialog.dismiss()

                (activity as MainActivity).getDatabase().insertScore(score)
                (activity as MainActivity).getDatabase().readAllScore.observe(viewLifecycleOwner, {
                    Cache.setCache(it)
                })

                Constant.showResultDialog(score, view.context)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private suspend fun compute(): Score {
        var time1 = 0L
        var time2 = 0L
        var time3 = 0L
        var time4 = 0L

        runningComputation = CoroutineScope(Dispatchers.Default).launch {
            progressMessage = CoroutineScope(Dispatchers.Main).launch {
                progressBarInfo.text = Constant.JOB1_MESSAGE
            }
            time1 = measureTimeMillis {
                Benchmark.primalityTest()
            }

            progressMessage = CoroutineScope(Dispatchers.Main).launch {
                progressBarInfo.text = Constant.JOB2_MESSAGE
            }
            time2 = measureTimeMillis {
                Benchmark.factorialCalculation()
            }

            progressMessage = CoroutineScope(Dispatchers.Main).launch {
                progressBarInfo.text = Constant.JOB3_MESSAGE
            }
            time3 = measureTimeMillis {
                Benchmark.sorting()
            }

            progressMessage = CoroutineScope(Dispatchers.Main).launch {
                progressBarInfo.text = Constant.JOB4_MESSAGE
            }
            time4 = measureTimeMillis {
                Benchmark.matrixMultiplication()
            }
        }
        runningComputation.join()

        val duration = time1 + time2 + time3 + time4
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        return Score(duration, currentTime, time1, time2, time3, time4)
    }
}