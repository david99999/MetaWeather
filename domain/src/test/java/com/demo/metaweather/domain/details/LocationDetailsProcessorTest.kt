package com.demo.metaweather.domain.details

import com.demo.metaweather.data.datasources.remote.dto.LocationWeatherDTO
import com.demo.metaweather.data.repositories.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LocationDetailsProcessorTest {

    @Test
    fun `processor returns the weather details after data loaded`() = runBlocking {
        val weatherRepository = mockk<WeatherRepository>().apply {
            coEvery { getWeatherForLocation(any()) } returns LocationWeatherDTO()
        }
        val processor = LocationDetailsProcessor(weatherRepository)
        val emittedFlow = processor.processEffect(LoadLocationDetailsEffect(0))
        assert(emittedFlow.last() is LocationDetailActions.ShowLocationWeather)
    }

    @Test
    fun `processor returns error when something went wrong when loading data`() = runBlocking {
        val weatherRepository = mockk<WeatherRepository>().apply {
            coEvery { getWeatherForLocation(any()) } throws Exception()
        }
        val processor = LocationDetailsProcessor(weatherRepository)
        val emittedFlow = processor.processEffect(LoadLocationDetailsEffect(0))
        assert(emittedFlow.last() is LocationDetailActions.Error)
    }
}
