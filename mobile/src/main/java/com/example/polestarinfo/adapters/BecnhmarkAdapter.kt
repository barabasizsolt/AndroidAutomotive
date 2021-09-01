package com.example.polestarinfo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polestarinfo.R
import com.example.polestarinfo.constants.Constant
import com.example.polestarinfo.model.Score
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BenchmarkAdapter (private val mList: List<Score>) : RecyclerView.Adapter<BenchmarkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.benchmark_result_recycler_view_item, parent, false)
        return ViewHolder(view).listen { position, _ ->
            val item = mList[position]

            //Alert dialog.
            val totalScore = "Total score: " + item.score
            val benchmarkTime = item.name
            val primalityScore = "Primality test score: " + item.primalityScore
            val factorialScore = "Factorial calculation score: " + item.factorialScore
            val sortingScore = "List sorting score: " + item.sortingScore
            val matrixScore = "Matrix multiplication score: " + item.matrixScore

            val items = arrayOf(benchmarkTime, totalScore, primalityScore, factorialScore, sortingScore, matrixScore)
            MaterialAlertDialogBuilder(view.context, R.style.AlertDialogTheme)
                .setTitle(R.string.benchmark_result_dialog_title)
                .setItems(items) { _, _ -> }
                .setPositiveButton(R.string.benchmark_result_dialog_positive_button) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
                .window!!.setLayout(Constant.BENCHMARK_RESULT_DIALOG_WIDTH, Constant.BENCHMARK_RESULT_DIALOG_HEIGHT)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        val totalScore = "Total score: " + itemsViewModel.score.toString()
        holder.benchmarkScore.text = totalScore
        holder.benchmarkName.text = itemsViewModel.name
    }

    override fun getItemCount(): Int = mList.size

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val benchmarkScore: TextView = itemView.findViewById(R.id.benchmark_score)
        val benchmarkName: TextView = itemView.findViewById(R.id.benchmark_name)
    }

    //Extension function to set the onClickListener.
    private fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(bindingAdapterPosition, itemViewType)
        }
        return this
    }
}