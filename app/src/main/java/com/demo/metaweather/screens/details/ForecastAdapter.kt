package com.demo.metaweather.screens.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.demo.metaweather.R
import com.demo.metaweather.data.datasources.remote.utils.Constants
import com.demo.metaweather.databinding.ItemForecastWeatherBinding
import com.demo.metaweather.domain.models.ConsolidatedWeather
import com.demo.metaweather.utils.DateFormatter
import com.demo.metaweather.utils.ImageLoader

/**
 * RecyclerView adapter for the forecasts of an specific location weather details,
 * it keeps track of the selected forecast so its background can be highlighted
 */
class ForecastAdapter(
    private val forecasts: List<ConsolidatedWeather>,
    private val clickListener: (ConsolidatedWeather) -> Unit,
    var selectedForecast: ConsolidatedWeather,
    private val dateFormatter: DateFormatter,
    private val imageLoader: ImageLoader
) : Adapter<ForecastItemViewHolder>() {

    override fun getItemCount() = forecasts.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ForecastItemViewHolder {
        val view = ItemForecastWeatherBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ForecastItemViewHolder(view.root)
    }

    override fun onBindViewHolder(viewHolder: ForecastItemViewHolder, position: Int) =
        with(viewHolder) {
            val forecast = forecasts[position]
            dateText.text = dateFormatter.getLongDateFromString(forecast.applicableDate)
            stateName.text = forecast.weatherStateName
            imageLoader.loadImageIntoImageView(
                Constants.weatherIcon(forecast.weatherStateAbbr),
                stateIcon
            )
            temperature.text =
                temperature.context.getString(R.string.temperature_value, forecast.theTemp)
            itemView.setOnClickListener { clickListener(forecast) }
            containerCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    containerCard.context,
                    if (selectedForecast == forecast) R.color.blue_200_light else R.color.white
                )
            )
        }
}

class ForecastItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val containerCard = view as CardView
    val dateText: TextView = view.findViewById(R.id.locationWeatherDate)
    val stateName: TextView = view.findViewById(R.id.locationStateName)
    val stateIcon: ImageView = view.findViewById(R.id.weatherIcon)
    val temperature: TextView = view.findViewById(R.id.locationTemperature)
}
