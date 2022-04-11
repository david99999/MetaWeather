package com.demo.metaweather.domain.details

import com.demo.metaweather.data.repositories.WeatherRepository
import com.demo.metaweather.domain.models.ConsolidatedWeather
import com.demo.metaweather.domain.models.LocationWeather
import com.general.mvi.EffectsProcessor
import com.general.mvi.NextState
import com.general.mvi.StateMapper
import com.general.mvi.StateUpdater
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

/**
 * Represents the possible states of the Location Weather Details screen
 */
sealed class LocationDetailsState {
    object LoadingDetails : LocationDetailsState()
    data class LocationWeatherDetails(
        val weather: LocationWeather,
        val selectedWeatherDetails: ConsolidatedWeather
    ) : LocationDetailsState()
}

/**
 * To be used when requesting the load of location details
 */
data class LoadLocationDetailsEffect(val whereOnEarthId: Int)

/**
 * Represents the actions executed on the Details screen
 */
sealed class LocationDetailActions {
    data class LoadLocationWeather(val whereOnEarthId: Int) : LocationDetailActions()
    data class SelectForecast(val consolidatedWeather: ConsolidatedWeather) :
        LocationDetailActions()

    data class ShowLocationWeather(val locationWeather: LocationWeather) : LocationDetailActions()
    data class Error(val error: Exception) : LocationDetailActions()
}

/**
 * Used for reporting to the UI that some event happened, mostly error notifying
 */
sealed class LocationDetailEvents {
    data class ErrorLoadingLocationDetails(val error: Exception) : LocationDetailEvents()
}

/**
 * Executes the long running tasks, every action performed from the processor will be running outside the UI thread
 */
class LocationDetailsProcessor @Inject constructor(private val weatherRepository: WeatherRepository) :
    EffectsProcessor<LoadLocationDetailsEffect, LocationDetailActions> {
    override suspend fun processEffect(effect: LoadLocationDetailsEffect): Flow<LocationDetailActions> =
        flow {
            try {
                // Load from the repository the location details, then convert the DTO
                // returned from the repository onto the domain model expected on the screen state
                val locationWeather = weatherRepository.getWeatherForLocation(
                    effect.whereOnEarthId
                ).let {
                    val forecasts = it.consolidatedWeatherDTO.map { dto ->
                        ConsolidatedWeather(
                            weatherStateName = dto.weatherStateName.orEmpty(),
                            weatherStateAbbr = dto.weatherStateAbbr.orEmpty(),
                            applicableDate = dto.applicableDate.orEmpty(),
                            minTemp = dto.minTemp ?: 0.toDouble(),
                            maxTemp = dto.maxTemp ?: 0.toDouble(),
                            theTemp = dto.theTemp ?: 0.toDouble()
                        )
                    }
                    LocationWeather(
                        forecasts = forecasts,
                        locationParent = it.locationParentDTO?.title.orEmpty(),
                        title = it.title.orEmpty()
                    )
                }
                // finish the long running task reporting the weather to the
                // [LocationDetailsUpdater] so it can set the data on the Screen State
                emit(LocationDetailActions.ShowLocationWeather(locationWeather))
            } catch (ex: Exception) {
                emit(LocationDetailActions.Error(ex))
            }
        }
}

/**
 * This is the only element that mutates the UI state,
 * so any action defined for the Details screen will be handled here,
 * after an action mutates the state its observers will be notified and will update the UI accordingly
 */
class LocationDetailsUpdater :
    StateUpdater<LocationDetailsState, LocationDetailActions, LoadLocationDetailsEffect, LocationDetailEvents> {
    override fun processNewAction(
        action: LocationDetailActions,
        currentState: LocationDetailsState
    ): NextState<LocationDetailsState, LoadLocationDetailsEffect, LocationDetailEvents> {
        return when (action) {
            is LocationDetailActions.LoadLocationWeather -> loadLocationWeather(action)
            is LocationDetailActions.ShowLocationWeather -> showLocationWeather(action)
            is LocationDetailActions.SelectForecast -> selectForecast(action, currentState)
            is LocationDetailActions.Error -> handleErrorLoadingDetails(action)
        }
    }

    /**
     * Executed when the used clicked a forecast item, so we need to set it as the selected forecast on the UI state
     */
    private fun selectForecast(
        action: LocationDetailActions.SelectForecast,
        currentState: LocationDetailsState
    ): NextState<LocationDetailsState, LoadLocationDetailsEffect, LocationDetailEvents> {
        return if (currentState is LocationDetailsState.LocationWeatherDetails) {
            NextState(currentState.copy(selectedWeatherDetails = action.consolidatedWeather))
        } else {
            NextState(currentState)
        }
    }

    private fun loadLocationWeather(action: LocationDetailActions.LoadLocationWeather): NextState<LocationDetailsState, LoadLocationDetailsEffect, LocationDetailEvents> {
        return NextState(
            LocationDetailsState.LoadingDetails,
            setOf(LoadLocationDetailsEffect(action.whereOnEarthId))
        )
    }

    private fun showLocationWeather(action: LocationDetailActions.ShowLocationWeather): NextState<LocationDetailsState, LoadLocationDetailsEffect, LocationDetailEvents> {
        return NextState(
            LocationDetailsState.LocationWeatherDetails(
                action.locationWeather,
                action.locationWeather.forecasts.first()
            )
        )
    }

    private fun handleErrorLoadingDetails(action: LocationDetailActions.Error): NextState<LocationDetailsState, LoadLocationDetailsEffect, LocationDetailEvents> {
        return NextState(
            LocationDetailsState.LoadingDetails,
            events = setOf(LocationDetailEvents.ErrorLoadingLocationDetails(action.error))
        )
    }
}

class LocationDetailsMapper : StateMapper<LocationDetailsState, LocationDetailsState> {
    override fun mapToUiState(state: LocationDetailsState): LocationDetailsState {
        return state
    }
}
