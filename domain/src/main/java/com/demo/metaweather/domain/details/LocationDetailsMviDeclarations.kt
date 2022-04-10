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

sealed class LocationDetailsState {
    object LoadingDetails : LocationDetailsState()
    data class LocationWeatherDetails(
        val weather: LocationWeather,
        val selectedWeatherDetails: ConsolidatedWeather
    ) : LocationDetailsState()
}

data class LoadLocationDetailsEffect(val whereOnEarthId: Int)

sealed class LocationDetailActions {
    data class LoadLocationWeather(val whereOnEarthId: Int) : LocationDetailActions()
    data class ShowLocationWeather(val locationWeather: LocationWeather) : LocationDetailActions()
    data class Error(val error: Exception) : LocationDetailActions()
}

sealed class LocationDetailEvents {
    data class ErrorLoadingLocationDetails(val error: Exception) : LocationDetailEvents()
}

class LocationDetailsProcessor @Inject constructor(private val weatherRepository: WeatherRepository) :
    EffectsProcessor<LoadLocationDetailsEffect, LocationDetailActions> {
    override suspend fun processEffect(effect: LoadLocationDetailsEffect): Flow<LocationDetailActions> =
        flow {
            try {
                emit(
                    LocationDetailActions.ShowLocationWeather(
                        weatherRepository.getWeatherForLocation(
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
                        })
                )
            } catch (ex: Exception) {
                emit(LocationDetailActions.Error(ex))
            }
        }

}


class LocationDetailsUpdater :
    StateUpdater<LocationDetailsState, LocationDetailActions, LoadLocationDetailsEffect, LocationDetailEvents> {
    override fun processNewAction(
        action: LocationDetailActions,
        currentState: LocationDetailsState
    ): NextState<LocationDetailsState, LoadLocationDetailsEffect, LocationDetailEvents> {
        return when (action) {
            is LocationDetailActions.LoadLocationWeather -> loadLocationWeather(action)
            is LocationDetailActions.ShowLocationWeather -> showLocationWeather(action)
            is LocationDetailActions.Error -> handleErrorLoadingDetails(action)
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
