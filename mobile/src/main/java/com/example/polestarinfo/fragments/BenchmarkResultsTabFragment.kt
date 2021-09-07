package com.example.polestarinfo.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polestarinfo.activities.MainActivity
import com.example.polestarinfo.R
import com.example.polestarinfo.adapters.BenchmarkAdapter
import com.example.polestarinfo.cache.Cache
import com.example.polestarinfo.constants.Constant
import com.example.polestarinfo.interfaces.OnItemClickListener
import com.example.polestarinfo.interfaces.OnItemLongClickListener
import com.example.polestarinfo.models.Score
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BenchmarkResultsTabFragment : Fragment(), OnItemClickListener, OnItemLongClickListener {
    private var scoresList: MutableList<Score> = if (Cache.getCache().isEmpty()) mutableListOf() else Cache.getCache()
    private lateinit var adapter: BenchmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_benchmark_results_tab, container, false)

        scoresList.sortByDescending { it.name }

        val recyclerview = view.findViewById<RecyclerView>(R.id.benchmark_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        adapter = BenchmarkAdapter(scoresList, this, this)
        recyclerview.adapter = adapter

        return view
    }

    override fun onItemClick(position: Int) {
        Constant.showResultDialog(scoresList[position], requireContext())
    }

    override fun onItemLongClick(position: Int) {
        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setMessage(R.string.benchmark_delete_dialog_message)
            .setPositiveButton(R.string.benchmark_delete_dialog_positive_button) { dialog, _ ->
                (activity as MainActivity).getDatabase().deleteScore(scoresList[position].name)
                adapter.removeAt(position)
                dialog.dismiss()
            }
            .show()
            .window!!.setLayout(Constant.DELETE_DIALOG_WIDTH, Constant.DELETE_DIALOG_HEIGHT)
    }
}