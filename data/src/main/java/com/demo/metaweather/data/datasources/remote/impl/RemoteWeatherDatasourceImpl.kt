package com.demo.metaweather.data.datasources.remote.impl

import com.demo.metaweather.data.datasources.remote.RemoteWeatherDatasource
import com.demo.metaweather.data.datasources.remote.dto.LocationAnswerDTO
import com.demo.metaweather.data.datasources.remote.dto.LocationWeatherDTO
import com.demo.metaweather.data.datasources.remote.impl.network.MetaWeatherNetworkApi
import javax.inject.Inject

class RemoteWeatherDatasourceImpl @Inject constructor(private val metaWeatherNetworkApi: MetaWeatherNetworkApi) :
    RemoteWeatherDatasource {

    override suspend fun searchLocationsByName(locationName: String): List<LocationAnswerDTO> {
        return metaWeatherNetworkApi.searchLocationsByName(locationName)
    }

    override suspend fun getWeatherForLocation(whereOnEarthId: Int): LocationWeatherDTO {
        return metaWeatherNetworkApi.getWeatherForLocation(whereOnEarthId)
    }
}
