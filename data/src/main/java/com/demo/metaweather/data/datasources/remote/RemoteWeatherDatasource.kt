package com.demo.metaweather.data.datasources.remote

import com.demo.metaweather.data.datasources.remote.dto.LocationAnswerDTO
import com.demo.metaweather.data.datasources.remote.dto.LocationWeatherDTO

/**
 * Definition of what a remote datasource should provide
 */
interface RemoteWeatherDatasource {

    suspend fun searchLocationsByName(locationName: String): List<LocationAnswerDTO>

    suspend fun getWeatherForLocation(whereOnEarthId: Int): LocationWeatherDTO
}
