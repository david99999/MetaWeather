package com.demo.metaweather.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.demo.metaweather.databinding.FragmentLocationDetailsBinding
import com.demo.metaweather.domain.details.LocationDetailActions
import com.demo.metaweather.domain.details.LocationDetailEvents
import com.demo.metaweather.domain.details.LocationDetailsState
import com.demo.metaweather.domain.details.LocationDetailsState.LocationWeatherDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationDetailsFragment : Fragment() {

    private lateinit var content: FragmentLocationDetailsBinding
    private val locationArgs: LocationDetailsFragmentArgs by navArgs()
    private val detailsViewModel: LocationDetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        content = FragmentLocationDetailsBinding.inflate(inflater, parent, false)
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            launch { detailsViewModel.getUiStateFlow().collect { updateUiState(it) } }
            launch { detailsViewModel.getEventsFlow().collect { handleDetailsEvents(it) } }
        }
        detailsViewModel.dispatchAction(LocationDetailActions.LoadLocationWeather(locationArgs.whereOnEarthId))
        return content.root
    }

    private fun updateUiState(state: LocationDetailsState) {
        when (state) {
            LocationDetailsState.LoadingDetails -> toggleLoadingDetails(true)
            is LocationWeatherDetails -> showLocationWeather(state)
        }
    }

    private fun showLocationWeather(state: LocationWeatherDetails) = with(content) {
        toggleLoadingDetails(false)
        locationNameAndParent.text = state.weather.title + " - " + state.weather.locationParent
        locationTemperature.text = state.selectedWeatherDetails.theTemp.toString() + "°C"
    }

    private fun toggleLoadingDetails(show: Boolean) {
        content.locationDetailsLoading.isVisible = show
        content.locationDetailsContainer.isVisible = !show
    }

    private fun handleDetailsEvents(event: LocationDetailEvents) {
        if (event is LocationDetailEvents.ErrorLoadingLocationDetails) {
            event.error.printStackTrace()
        }
    }

}
