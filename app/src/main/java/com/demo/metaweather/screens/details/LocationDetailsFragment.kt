package com.demo.metaweather.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.demo.metaweather.R
import com.demo.metaweather.data.datasources.remote.utils.Constants
import com.demo.metaweather.databinding.FragmentLocationDetailsBinding
import com.demo.metaweather.domain.details.LocationDetailActions
import com.demo.metaweather.domain.details.LocationDetailEvents
import com.demo.metaweather.domain.details.LocationDetailsState
import com.demo.metaweather.domain.details.LocationDetailsState.LocationWeatherDetails
import com.demo.metaweather.domain.models.ConsolidatedWeather
import com.demo.metaweather.utils.DateFormatter
import com.demo.metaweather.utils.ImageLoader
import com.demo.metaweather.utils.ensureContentCentered
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Fragment displaying the locations weather details,
 * it also shows a list of the weather forecast for the current and next 4 days
 */
@AndroidEntryPoint
class LocationDetailsFragment : Fragment() {

    private lateinit var content: FragmentLocationDetailsBinding
    private val locationArgs: LocationDetailsFragmentArgs by navArgs()
    private val detailsViewModel: LocationDetailsViewModel by viewModels()

    @Inject
    lateinit var datesFormatter: DateFormatter

    @Inject
    lateinit var imageLoader: ImageLoader

    /**
     * Adapter for the recyclerView
     */
    private var forecastAdapter: ForecastAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        content = FragmentLocationDetailsBinding.inflate(inflater, parent, false)
        // start listening for UI state changes
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            launch { detailsViewModel.getUiStateFlow().collect { updateUiState(it) } }
            launch { detailsViewModel.getEventsFlow().collect { handleDetailsEvents(it) } }
        }
        ensureContentCentered(content.detailsScrollableContent.layoutParams as? FrameLayout.LayoutParams)
        if (state == null) {
            detailsViewModel.dispatchAction(LocationDetailActions.LoadLocationWeather(locationArgs.whereOnEarthId))
        }
        return content.root
    }

    /**
     * This method will be called everytime the UI state has changed,
     * so we update the UI content accordingly to the new values
     */
    private fun updateUiState(state: LocationDetailsState) {
        when (state) {
            LocationDetailsState.LoadingDetails -> toggleLoadingDetails(true)
            is LocationWeatherDetails -> showLocationWeather(state)
        }
    }

    /**
     * Shows the weather details on every view of the content
     */
    private fun showLocationWeather(state: LocationWeatherDetails) = with(content) {
        toggleLoadingDetails(false)
        locationNameAndParent.text = getString(
            R.string.location_and_parent,
            state.weather.title,
            state.weather.locationParent
        )
        locationTemperature.text =
            getString(R.string.temperature_value, state.selectedWeatherDetails.theTemp)
        locationWeatherDate.text =
            datesFormatter.getLongDateFromString(state.selectedWeatherDetails.applicableDate)
        locationStateName.text = state.selectedWeatherDetails.weatherStateName

        imageLoader.loadImageIntoImageView(
            Constants.weatherIcon(state.selectedWeatherDetails.weatherStateAbbr),
            weatherIcon
        )

        weatherTempRange.text = getString(
            R.string.temperature_range,
            state.selectedWeatherDetails.minTemp,
            state.selectedWeatherDetails.maxTemp
        )
        setupWeatherForecast(state)
    }

    /**
     * Setup the forecasts recyclerview depending if we need to
     * create the adapter or only update the selected forecast
     */
    private fun setupWeatherForecast(state: LocationWeatherDetails) {
        if (forecastAdapter == null) {
            forecastAdapter = ForecastAdapter(
                state.weather.forecasts,
                ::forecastClickListener,
                state.selectedWeatherDetails,
                datesFormatter,
                imageLoader
            )
            content.locationForecastRecycler.adapter = forecastAdapter
        } else {
            forecastAdapter?.selectedForecast = state.selectedWeatherDetails
            forecastAdapter?.notifyDataSetChanged()
        }
    }

    /**
     * Dispatches an action for setting the clicked forecast item
     * as the selected forecast on the UI content
     */
    private fun forecastClickListener(consolidatedWeather: ConsolidatedWeather) {
        detailsViewModel.dispatchAction(LocationDetailActions.SelectForecast(consolidatedWeather))
    }

    private fun toggleLoadingDetails(show: Boolean) {
        content.locationDetailsLoading.isVisible = show
        content.locationDetailsContainer.isVisible = !show
    }

    /**
     * Used when an error happens, so we can notify the user something went wrong
     */
    private fun handleDetailsEvents(event: LocationDetailEvents) {
        if (event is LocationDetailEvents.ErrorLoadingLocationDetails) {
            Snackbar.make(
                content.root,
                getString(R.string.unexpected_error, event.error.localizedMessage),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}
