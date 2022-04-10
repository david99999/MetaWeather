package com.demo.metaweather.screens.search

import com.demo.metaweather.domain.search.LoadLocationsEffect
import com.demo.metaweather.domain.search.LocationEvents
import com.demo.metaweather.domain.search.LocationsActions
import com.demo.metaweather.domain.search.LocationsProcessor
import com.demo.metaweather.domain.search.LocationsScreenState
import com.demo.metaweather.domain.search.LocationsScreenState.EmptyLocationsList
import com.demo.metaweather.domain.search.LocationsStateMapper
import com.demo.metaweather.domain.search.LocationsUpdater
import com.general.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(locationsProcessor: LocationsProcessor) :
    MviViewModel<LocationsActions, LocationsScreenState, LocationsScreenState, LoadLocationsEffect, LocationEvents>(
        stateUpdater = LocationsUpdater(),
        effectsProcessor = locationsProcessor,
        stateMapper = LocationsStateMapper(),
        initialState = EmptyLocationsList(noResults = false),
        initialEffects = setOf()
    )
