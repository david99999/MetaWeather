package com.demo.metaweather.domain.search

import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.Exception

@RunWith(JUnit4::class)
class LocationsUpdaterTest {

    private val locationSearchUpdater = LocationsUpdater()

    @Test
    fun `updater requests locations search when search action is dispatched`() {
        val updatedState = locationSearchUpdater
            .processNewAction(LocationsActions.LoadLocationsByName("london"), mockk())

        assert(updatedState.state is LocationsScreenState.LoadingLocations)
        assert(updatedState.effects.isNotEmpty())
    }

    @Test
    fun `updater doesn't request locations search when search action is dispatched with empty string`() {
        val updatedState = locationSearchUpdater
            .processNewAction(LocationsActions.LoadLocationsByName(""), mockk())

        assert(updatedState.effects.isEmpty())
    }

    @Test
    fun `updater sets the location results into the state when data loaded and is data is not empty`() {
        val updatedState =
            locationSearchUpdater.processNewAction(
                LocationsActions.ShowLocationsAction(
                    listOf(mockk())
                ),
                mockk()
            )

        assert(updatedState.state is LocationsScreenState.ShowingLocationResults)
        Assert.assertNotNull(updatedState.state)
    }

    @Test
    fun `updater sets empty location results into the state when data loaded and is data is empty`() {
        val updatedState =
            locationSearchUpdater.processNewAction(
                LocationsActions.ShowLocationsAction(
                    emptyList()
                ),
                mockk()
            )

        assert(updatedState.state is LocationsScreenState.EmptyLocationsList)
        Assert.assertNotNull(updatedState.state)
    }

    @Test
    fun `updater notifies error when something went wrong searching for locations by name`() {
        val updatedState =
            locationSearchUpdater.processNewAction(
                LocationsActions.Error(
                    Exception()
                ),
                mockk()
            )

        assert(updatedState.events.any { it is LocationEvents.ErrorLoadingLocations })
    }
}
