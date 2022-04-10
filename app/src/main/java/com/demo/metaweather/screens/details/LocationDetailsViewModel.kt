package com.demo.metaweather.screens.details

import com.demo.metaweather.domain.details.LoadLocationDetailsEffect
import com.demo.metaweather.domain.details.LocationDetailActions
import com.demo.metaweather.domain.details.LocationDetailEvents
import com.demo.metaweather.domain.details.LocationDetailsMapper
import com.demo.metaweather.domain.details.LocationDetailsProcessor
import com.demo.metaweather.domain.details.LocationDetailsState
import com.demo.metaweather.domain.details.LocationDetailsUpdater
import com.general.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(locationDetailsProcessor: LocationDetailsProcessor) :
    MviViewModel<LocationDetailActions, LocationDetailsState, LocationDetailsState, LoadLocationDetailsEffect, LocationDetailEvents>(
        stateUpdater = LocationDetailsUpdater(),
        effectsProcessor = locationDetailsProcessor,
        stateMapper = LocationDetailsMapper(),
        initialState = LocationDetailsState.LoadingDetails
    )
