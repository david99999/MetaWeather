package com.demo.metaweather.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.demo.metaweather.R
import com.demo.metaweather.databinding.FragmentLocationSearchBinding
import com.demo.metaweather.domain.models.LocationItem
import com.demo.metaweather.domain.search.LocationEvents
import com.demo.metaweather.domain.search.LocationsActions
import com.demo.metaweather.domain.search.LocationsScreenState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationSearchFragment : Fragment() {

    private lateinit var content: FragmentLocationSearchBinding
    private val locationsViewModel: LocationSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        content = FragmentLocationSearchBinding.inflate(inflater, parent, false)
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            launch { locationsViewModel.getUiStateFlow().collect { updateUiState(it) } }
            launch { locationsViewModel.getEventsFlow().collect { handleLocationsEvents(it) } }
        }
        return content.root
    }

    private fun handleLocationsEvents(event: LocationEvents) {
        if (event is LocationEvents.ErrorLoadingLocations) {
            Snackbar.make(
                content.locationSearchResults,
                getString(R.string.unexpected_error, event.error.localizedMessage),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun updateUiState(screenState: LocationsScreenState) {
        when (screenState) {
            is LocationsScreenState.LoadingLocations -> showLoadingResults()
            is LocationsScreenState.ShowingLocationResults -> showLocationResults(screenState.locations)
            is LocationsScreenState.EmptyLocationsList -> showEmptyState(screenState)
        }
    }

    private fun showEmptyState(emptyState: LocationsScreenState.EmptyLocationsList) =
        with(content) {
            locationSearchLoading.isVisible = false
            locationSearchResults.isVisible = true
            locationSearchResults.adapter = EmptyLocationsAdapter(emptyState.noResults)
        }

    private fun showLocationResults(locations: List<LocationItem>) = with(content) {
        locationSearchLoading.isVisible = false
        locationSearchResults.isVisible = true
        locationSearchResults.adapter = LocationsResultAdapter(locations, ::locationClickListener)
    }

    private fun locationClickListener(locationItem: LocationItem) {
        findNavController().navigate(
            LocationSearchFragmentDirections.openLocationDetails(
                locationName = locationItem.name,
                whereOnEarthId = locationItem.whereOnEarthId
            )
        )
    }

    private fun showLoadingResults() = with(content) {
        locationSearchLoading.isVisible = true
        locationSearchResults.isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.location_search_menu, menu)
        val searchView = menu.findItem(R.id.locationSearchMenuItem).actionView as SearchView
        searchView.queryHint = getString(R.string.location_search_query)
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                locationsViewModel.dispatchAction(LocationsActions.LoadLocationsByName(newText))
                return true
            }
        })
    }
}
