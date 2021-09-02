package com.example.polestarinfo.adapters

import android.hardware.Sensor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polestarinfo.R
import com.example.polestarinfo.constants.Constant
import com.example.polestarinfo.interfaces.OnItemClickListener
import com.example.polestarinfo.interfaces.OnItemLongClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SensorAdapter (private val mList: List<Sensor>, onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<SensorAdapter.ViewHolder>() {

    private val mOnItemClickListener: OnItemClickListener = onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)

        return ViewHolder(view, mOnItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.sensorName.text = itemsViewModel.name
        holder.vendor.text = itemsViewModel.vendor
    }

    override fun getItemCount(): Int = mList.size

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val sensorName: TextView = itemView.findViewById(R.id.sensor_name)
        val vendor: TextView = itemView.findViewById(R.id.vendor)
        private val mOnItemClickListener: OnItemClickListener = onItemClickListener

        override fun onClick(view: View) {
            mOnItemClickListener.onItemClick(bindingAdapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}