package com.demo.metaweather.domain.search

import com.demo.metaweather.data.datasources.remote.dto.LocationAnswerDTO
import com.demo.metaweather.data.repositories.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LocationsProcessorTest {

    @Test
    fun `processor returns the weather details after data loaded`() = runBlocking {
        val weatherRepository = mockk<WeatherRepository>().apply {
            coEvery { searchLocationsByName(any()) } returns listOf(LocationAnswerDTO())
        }
        val processor = LocationsProcessor(weatherRepository)
        val emittedFlow = processor.processEffect(LoadLocationsEffect("london"))
        assert(emittedFlow.last() is LocationsActions.ShowLocationsAction)
    }

    @Test
    fun `processor returns error when something went wrong when loading data`() = runBlocking {
        val weatherRepository = mockk<WeatherRepository>().apply {
            coEvery { searchLocationsByName(any()) } throws Exception()
        }
        val processor = LocationsProcessor(weatherRepository)
        val emittedFlow = processor.processEffect(LoadLocationsEffect("london"))
        assert(emittedFlow.last() is LocationsActions.Error)
    }
}
