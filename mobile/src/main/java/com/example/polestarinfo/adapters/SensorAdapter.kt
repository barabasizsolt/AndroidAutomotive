package com.example.polestarinfo.adapters

import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polestarinfo.R
import com.example.polestarinfo.constants.Constant
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SensorAdapter (private val mList: List<Sensor>) : RecyclerView.Adapter<SensorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view).listen { position, _ ->
            val item = mList[position]

            //Alert dialog.
            val name = item.name
            val vendor = "Vendor: " + item.vendor
            val version = "Version:" + item.version
            val type = "Type: " + item.type
            val maxRange = "Maximum range: " + item.maximumRange
            val res = "Resolution: " + item.resolution
            val power = "Power: " + item.power
            val minDelay = "Minimum delay: " + item.minDelay

            val items = arrayOf(name, vendor, version, type, maxRange, res, power, minDelay)
            MaterialAlertDialogBuilder(view.context, R.style.AlertDialogTheme)
                .setTitle(R.string.sensor_title)
                .setItems(items) { _, _ -> }
                .show()
                .window!!.setLayout(Constant.DIALOG_WIDTH, Constant.DIALOG_HEIGHT)
        }
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

    //Extension function to set the onClickListener.
    private fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(bindingAdapterPosition, itemViewType)
        }
        return this
    }
}