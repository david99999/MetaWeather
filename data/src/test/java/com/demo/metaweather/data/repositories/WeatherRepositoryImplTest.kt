package com.demo.metaweather.data.repositories

import com.demo.metaweather.data.datasources.remote.RemoteWeatherDatasource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WeatherRepositoryImplTest {
    private val dataSource: RemoteWeatherDatasource = mockk()
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun init() {
        coEvery { dataSource.getWeatherForLocation(any()) } returns mockk()
        coEvery { dataSource.searchLocationsByName(any()) } returns mockk()
        repository = WeatherRepositoryImpl(dataSource)
    }

    @Test
    fun `repository fetches location from remote Data Source when requested locations search`() =
        runBlocking {
            repository.searchLocationsByName("london")
            coVerify(exactly = 1) { dataSource.searchLocationsByName(any()) }
        }

    @Test
    fun `repository fetches location details from remote Data Source when requested location details`() =
        runBlocking {
            repository.getWeatherForLocation(0)
            coVerify(exactly = 1) { dataSource.getWeatherForLocation(any()) }
        }
}
