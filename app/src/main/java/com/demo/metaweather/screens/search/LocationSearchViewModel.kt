package com.demo.metaweather.screens.search

import com.demo.metaweather.domain.search.*
import com.general.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(locationsProcessor: LocationsProcessor) :
    MviViewModel<LocationsActions, LocationsScreenState, LocationsScreenState, LoadLocationsEffect, LocationEvents>(
        stateUpdater = LocationsUpdater(),
        effectsProcessor = locationsProcessor,
        stateMapper = LocationsStateMapper(),
        initialState = LocationsScreenState.LoadingLocations,
        initialEffects = setOf()
    )
