package com.demo.metaweather.screens.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.demo.metaweather.R
import com.demo.metaweather.databinding.ItemLocationResultBinding
import com.demo.metaweather.domain.models.LocationItem

class LocationsResultAdapter(
    private val locations: List<LocationItem>,
    private val clickListener: (LocationItem) -> Unit
) : Adapter<LocationResultViewHolder>() {

    override fun getItemCount() = locations.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LocationResultViewHolder {
        val view = ItemLocationResultBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return LocationResultViewHolder(view.root)
    }

    override fun onBindViewHolder(viewHolder: LocationResultViewHolder, position: Int) {
        val location = locations[position]
        viewHolder.name.text = location.name
        viewHolder.type.text = location.type
        viewHolder.itemView.setOnClickListener { clickListener(location) }
    }
}

class LocationResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.locationTitle)
    val type: TextView = view.findViewById(R.id.locationType)
}
