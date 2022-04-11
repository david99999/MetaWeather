package com.demo.metaweather.domain.details

import com.demo.metaweather.domain.details.LocationDetailActions.SelectForecast
import com.demo.metaweather.domain.details.LocationDetailActions.ShowLocationWeather
import com.demo.metaweather.domain.models.ConsolidatedWeather
import com.demo.metaweather.domain.models.LocationWeather
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.Exception

@RunWith(JUnit4::class)
class LocationDetailsUpdaterTest {

    private val locationDetailsUpdater = LocationDetailsUpdater()

    @Test
    fun `updater request details loading when load action is dispatched`() {
        val updatedState = locationDetailsUpdater
            .processNewAction(LocationDetailActions.LoadLocationWeather(0), mockk())

        assert(updatedState.state is LocationDetailsState.LoadingDetails)
        assert(updatedState.effects.isNotEmpty())
    }

    @Test
    fun `updater sets the location details into the state when data loaded`() {
        val mockDetails = mockk<LocationWeather>().apply {
            every { forecasts } returns (listOf(mockk()))
        }
        val updatedState =
            locationDetailsUpdater.processNewAction(ShowLocationWeather(mockDetails), mockk())

        assert(updatedState.state is LocationDetailsState.LocationWeatherDetails)
        assertNotNull(updatedState.state)
    }

    @Test
    fun `updater mutates the selected forecast details when new forecast is clicked`() {
        val stubPreviousState = LocationDetailsState.LocationWeatherDetails(
            weather = mockk(),
            selectedWeatherDetails = ConsolidatedWeather(
                "",
                "",
                "",
                0.toDouble(),
                0.toDouble(),
                0.toDouble()
            )
        )

        val updatedState =
            locationDetailsUpdater.processNewAction(SelectForecast(mockk()), stubPreviousState)

        assert(updatedState.state is LocationDetailsState.LocationWeatherDetails)
        with(updatedState.state as LocationDetailsState.LocationWeatherDetails) {
            assertNotEquals(selectedWeatherDetails, stubPreviousState.selectedWeatherDetails)
        }
    }

    @Test
    fun `updater notifies error when something went wrong loading details`() {
        val updatedState =
            locationDetailsUpdater.processNewAction(
                LocationDetailActions.Error(Exception()),
                mockk()
            )

        assert(updatedState.state is LocationDetailsState.LoadingDetails)
        assert(updatedState.events.any { it is LocationDetailEvents.ErrorLoadingLocationDetails })
    }
}
