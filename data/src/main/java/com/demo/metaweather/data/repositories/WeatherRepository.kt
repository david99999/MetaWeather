package com.demo.metaweather.data.repositories

import com.demo.metaweather.data.datasources.remote.RemoteWeatherDatasource
import com.demo.metaweather.data.datasources.remote.dto.LocationAnswerDTO
import com.demo.metaweather.data.datasources.remote.dto.LocationWeatherDTO
import javax.inject.Inject

/**
 * Repository for dealing with data sources, should orchestrate the caching (if exist)
 * and the remote data loading
 */
interface WeatherRepository {

    suspend fun searchLocationsByName(locationName: String): List<LocationAnswerDTO>

    suspend fun getWeatherForLocation(whereOnEarthId: Int): LocationWeatherDTO
}

class WeatherRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteWeatherDatasource) :
    WeatherRepository {
    override suspend fun searchLocationsByName(locationName: String): List<LocationAnswerDTO> {
        return remoteDataSource.searchLocationsByName(locationName)
    }

    override suspend fun getWeatherForLocation(whereOnEarthId: Int): LocationWeatherDTO {
        return remoteDataSource.getWeatherForLocation(whereOnEarthId)
    }
}
