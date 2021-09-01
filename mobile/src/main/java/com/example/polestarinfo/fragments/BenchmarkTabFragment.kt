package com.example.polestarinfo.fragments


import android.annotation.SuppressLint
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
import com.example.polestarinfo.MainActivity
import com.example.polestarinfo.R
import com.example.polestarinfo.benchmark.Benchmark
import com.example.polestarinfo.cache.Cache
import com.example.polestarinfo.constants.Constant
import com.example.polestarinfo.model.Score
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
    private lateinit var dialog: AlertDialog

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
        val sdk = "Sdk version: " + Build.VERSION.SDK
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

        dialog = MaterialAlertDialogBuilder(view.context, R.style.AlertDialogTheme)
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
                dialog.show()
                dialog.window!!.setLayout(Constant.BENCHMARK_DIALOG_WIDTH, Constant.BENCHMARK_DIALOG_HEIGHT)
                val score = compute()
                delay(1000)
                dialog.dismiss()

                (activity as MainActivity).getDatabase().insertScore(score)
                (activity as MainActivity).getDatabase().readAllScore.observe(viewLifecycleOwner, {
                    Cache.setCache(it)
                })

                //TODO: navigate to benchmarkResultsTabFragment.
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private suspend fun compute(): Score {
        val duration = measureTimeMillis {
            runningComputation = CoroutineScope(Dispatchers.Default).launch {
                progressMessage = CoroutineScope(Dispatchers.Main).launch {
                    progressBarInfo.text = Constant.job1Message
                }
                Benchmark.primalityTest()
                Log.d("Job1", "primality test done")

                progressMessage = CoroutineScope(Dispatchers.Main).launch {
                    progressBarInfo.text = Constant.job2Message
                }
                Benchmark.factorialCalculation()
                Log.d("Job2", "factorial calculation done")

                progressMessage = CoroutineScope(Dispatchers.Main).launch {
                    progressBarInfo.text = Constant.job3Message
                }
                Benchmark.sorting()
                Log.d("Job3", "list sorting done")

                progressMessage = CoroutineScope(Dispatchers.Main).launch {
                    progressBarInfo.text = Constant.job4Message
                }
                Benchmark.matrixMultiplication()
                Log.d("Job4", "matrix multiplication done")
            }
            runningComputation.join()
        }

        //TODO: formatter time bug.
        return Score(duration, SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time))
    }
}