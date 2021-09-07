package com.example.polestarinfo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polestarinfo.R
import com.example.polestarinfo.interfaces.OnItemClickListener
import com.example.polestarinfo.interfaces.OnItemLongClickListener
import com.example.polestarinfo.models.Score

class BenchmarkAdapter (private val mList: MutableList<Score>, onItemClickListener: OnItemClickListener, onItemLongClickListener: OnItemLongClickListener)
    : RecyclerView.Adapter<BenchmarkAdapter.ViewHolder>() {

    private val mOnItemClickListener: OnItemClickListener = onItemClickListener
    private val mOnItemLongClickListener: OnItemLongClickListener = onItemLongClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.benchmark_result_recycler_view_item, parent, false)
        return ViewHolder(view, mOnItemClickListener, mOnItemLongClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        val totalScore = "Total time: " + itemsViewModel.score.toString() + " ms"
        holder.benchmarkScore.text = totalScore
        holder.benchmarkName.text = itemsViewModel.name
    }

    override fun getItemCount(): Int = mList.size

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener, onItemLongClickListener: OnItemLongClickListener)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        val benchmarkScore: TextView = itemView.findViewById(R.id.benchmark_score)
        val benchmarkName: TextView = itemView.findViewById(R.id.benchmark_name)
        private val mOnItemClickListener: OnItemClickListener = onItemClickListener
        private val mOnItemLongClickListener: OnItemLongClickListener = onItemLongClickListener

        override fun onClick(view: View) {
            mOnItemClickListener.onItemClick(bindingAdapterPosition)
        }

        override fun onLongClick(view: View): Boolean {
            mOnItemLongClickListener.onItemLongClick(bindingAdapterPosition)
            return true
        }

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
    }

    fun removeAt(position: Int) {
        mList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mList.size)
    }
}