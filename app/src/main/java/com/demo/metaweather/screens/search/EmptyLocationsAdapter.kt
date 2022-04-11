package com.demo.metaweather.screens.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.demo.metaweather.R
import com.demo.metaweather.databinding.ItemEmptyLocationBinding

/**
 * Adapter for the showing an empty state, it will show the initial empty state message
 * and the empty state when the search didn't returned any results
 */
class EmptyLocationsAdapter(private val noResults: Boolean) : Adapter<EmptyLocationsViewHolder>() {

    override fun getItemCount() = 1

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EmptyLocationsViewHolder {
        val view = ItemEmptyLocationBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return EmptyLocationsViewHolder(view.root)
    }

    override fun onBindViewHolder(viewHolder: EmptyLocationsViewHolder, position: Int) {
        viewHolder.textView.setText(if (noResults) R.string.no_results else R.string.start_searching_for_locations)
        val iconResId = if (noResults) R.drawable.ic_no_results else R.drawable.ic_start_searching
        val topIcon = ContextCompat.getDrawable(viewHolder.textView.context, iconResId)
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null, topIcon, null, null)
    }
}

class EmptyLocationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.locationTitle)
}
