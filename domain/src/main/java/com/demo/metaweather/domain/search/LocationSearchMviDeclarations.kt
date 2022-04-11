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

/**
 * Represents the possible states of the Location Search screen
 */
sealed class LocationsScreenState {
    data class ShowingLocationResults(val locations: List<LocationItem>) : LocationsScreenState()
    object LoadingLocations : LocationsScreenState()
    data class EmptyLocationsList(val noResults: Boolean) : LocationsScreenState()
}

/**
 * To be used when requesting the search of Locations
 */
data class LoadLocationsEffect(val locationName: String)

/**
 * Represents the actions executed on the Locations search screen
 */
sealed class LocationsActions {
    data class LoadLocationsByName(val locationName: String) : LocationsActions()
    data class ShowLocationsAction(val locations: List<LocationItem>) : LocationsActions()
    data class Error(val error: Exception) : LocationsActions()
}

/**
 * Used for reporting to the UI that some event happened, mostly error notifying
 */
sealed class LocationEvents {
    data class ErrorLoadingLocations(val error: Exception) : LocationEvents()
}

/**
 * Executes the long running tasks, every action performed from the processor will be running outside the UI thread
 */
class LocationsProcessor @Inject constructor(private val weatherRepository: WeatherRepository) :
    EffectsProcessor<LoadLocationsEffect, LocationsActions> {
    override suspend fun processEffect(effect: LoadLocationsEffect): Flow<LocationsActions> =
        flow {
            try {
                // Load from the repository the location search results, then convert the DTO
                // returned from the repository onto the domain model expected on the screen state
                val locationResults = weatherRepository.searchLocationsByName(effect.locationName)
                    .map { dto ->
                        LocationItem(
                            name = dto.title.orEmpty(),
                            type = dto.locationType.orEmpty(),
                            whereOnEarthId = dto.whereOnEarthId ?: 0
                        )
                    }
                // finish the long running task reporting the locations to the
                // [LocationsUpdater] so it can set the data on the Screen State
                emit(ShowLocationsAction(locationResults))
            } catch (ex: Exception) {
                emit(Error(ex))
            }
        }
}

/**
 * This is the only element that mutates the UI state,
 * so any action defined for the Location Search screen will be handled here,
 * after an action mutates the state its observers will be notified and will update the UI accordingly
 */
class LocationsUpdater :
    StateUpdater<LocationsScreenState, LocationsActions, LoadLocationsEffect, LocationEvents> {
    override fun processNewAction(
        action: LocationsActions,
        currentState: LocationsScreenState
    ): NextState<LocationsScreenState, LoadLocationsEffect, LocationEvents> {
        return when (action) {
            is LoadLocationsByName -> loadLocationByName(action, currentState)
            is ShowLocationsAction -> showLocations(action)
            is Error -> handleErrorSearchingLocations(action, currentState)
        }
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

    private fun showLocations(action: ShowLocationsAction): NextState<LocationsScreenState, LoadLocationsEffect, LocationEvents> {
        return if (action.locations.isEmpty()) {
            NextState(LocationsScreenState.EmptyLocationsList(noResults = true))
        } else {
            NextState(ShowingLocationResults(action.locations))
        }
    }

    private fun handleErrorSearchingLocations(
        action: Error,
        currentState: LocationsScreenState
    ): NextState<LocationsScreenState, LoadLocationsEffect, LocationEvents> {
        return NextState(currentState, events = setOf(ErrorLoadingLocations(action.error)))
    }
}

class LocationsStateMapper : StateMapper<LocationsScreenState, LocationsScreenState> {
    override fun mapToUiState(state: LocationsScreenState): LocationsScreenState {
        return state
    }
}
