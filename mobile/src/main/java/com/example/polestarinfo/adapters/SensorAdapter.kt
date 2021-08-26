package com.example.polestarinfo.adapters

import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polestarinfo.R

class SensorAdapter(private val mList: List<Sensor>) : RecyclerView.Adapter<SensorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.sensorName.text = itemsViewModel.name
        holder.vendor.text = itemsViewModel.vendor
    }

    override fun getItemCount(): Int = mList.size

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val sensorName: TextView = itemView.findViewById(R.id.sensor_name)
        val vendor: TextView = itemView.findViewById(R.id.vendor)
    }
}