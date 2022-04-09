package com.demo.metaweather.domain.search

import com.demo.metaweather.data.repositories.WeatherRepository
import com.demo.metaweather.domain.models.LocationItem
import com.demo.metaweather.domain.search.LocationEvents.ErrorLoadingLocations
import com.demo.metaweather.domain.search.LocationsActions.Error
import com.demo.metaweather.domain.search.LocationsActions.LoadLocationsByName
import com.demo.metaweather.domain.search.LocationsActions.ShowLocationsAction
import com.demo.metaweather.domain.search.LocationsScreenState.ShowingLocationResults
import com.general.mvi.EffectsProcessor
import com.general.mvi.NextState
import com.general.mvi.StateMapper
import com.general.mvi.StateUpdater
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

sealed class LocationsScreenState {
    data class ShowingLocationResults(val locations: List<LocationItem>) : LocationsScreenState()
    object LoadingLocations : LocationsScreenState()
}

data class LoadLocationsEffect(val locationName: String)

sealed class LocationsActions {
    data class LoadLocationsByName(val locationName: String) : LocationsActions()
    data class ShowLocationsAction(val cities: List<LocationItem>) : LocationsActions()
    data class Error(val error: Exception) : LocationsActions()
}

sealed class LocationEvents {
    data class ErrorLoadingLocations(val error: Exception) : LocationEvents()
}

class LocationsProcessor @Inject constructor(private val weatherRepository: WeatherRepository) :
    EffectsProcessor<LoadLocationsEffect, LocationsActions> {
    override suspend fun processEffect(effect: LoadLocationsEffect): Flow<LocationsActions> =
        flow {
            try {
                emit(
                    ShowLocationsAction(
                        weatherRepository.searchLocationsByName(effect.locationName)
                            .map { dto ->
                                LocationItem(dto.title.orEmpty(), dto.whereOnEarthId ?: 0)
                            }
                    )
                )
            } catch (ex: Exception) {
                emit(Error(ex))
            }
        }
}

class LocationsUpdater :
    StateUpdater<LocationsScreenState, LocationsActions, LoadLocationsEffect, LocationEvents> {
    override fun processNewAction(
        action: LocationsActions,
        currentState: LocationsScreenState
    ): NextState<LocationsScreenState, LoadLocationsEffect, LocationEvents> {
        return when (action) {
            is ShowLocationsAction -> NextState(ShowingLocationResults(action.cities))
            is LoadLocationsByName -> loadLocationByName(action, currentState)
            is Error -> handleErrorSearchingLocations(action, currentState)
        }
    }

    private fun handleErrorSearchingLocations(
        action: Error,
        currentState: LocationsScreenState
    ): NextState<LocationsScreenState, LoadLocationsEffect, LocationEvents> {
        return NextState(currentState, events = setOf(ErrorLoadingLocations(action.error)))
    }

    private fun loadLocationByName(
        action: LoadLocationsByName,
        currentState: LocationsScreenState
    ): NextState<LocationsScreenState, LoadLocationsEffect, LocationEvents> {
        return if (action.locationName.isEmpty()) {
            NextState(currentState)
        } else {
            NextState(
                LocationsScreenState.LoadingLocations,
                setOf(LoadLocationsEffect(action.locationName))
            )
        }
    }
}

class LocationsStateMapper : StateMapper<LocationsScreenState, LocationsScreenState> {
    override fun mapToUiState(state: LocationsScreenState): LocationsScreenState {
        return state
    }
}
