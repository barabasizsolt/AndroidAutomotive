package com.example.polestarinfo.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polestarinfo.R
import com.example.polestarinfo.adapters.BenchmarkAdapter
import com.example.polestarinfo.cache.Cache

class BenchmarkResultsTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_benchmark_results_tab, container, false)
        Log.d("Scores", Cache.getCache().toString())

        val recyclerview = view.findViewById<RecyclerView>(R.id.benchmark_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        val adapter = BenchmarkAdapter(Cache.getCache().reversed())
        recyclerview.adapter = adapter

        return view
    }
}