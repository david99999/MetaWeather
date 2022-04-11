package com.demo.metaweather.data.datasources.remote.impl.network

import com.demo.metaweather.data.datasources.remote.RemoteWeatherDatasource
import com.demo.metaweather.data.datasources.remote.dto.LocationAnswerDTO
import com.demo.metaweather.data.datasources.remote.dto.LocationWeatherDTO
import com.demo.metaweather.data.datasources.remote.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Single endpoint to be consumed when fetching Metaweather API,
 * it only adds retrofit annotations to the already defined [RemoteWeatherDatasource]
 */
interface MetaWeatherNetworkApi : RemoteWeatherDatasource {

    @GET(Constants.LOCATION_SEARCH)
    override suspend fun searchLocationsByName(@Query(Constants.PARAM_QUERY) locationName: String): List<LocationAnswerDTO>

    @GET(Constants.LOCATION_WEATHER)
    override suspend fun getWeatherForLocation(@Path(Constants.WHERE_ON_EARTH_ID) whereOnEarthId: Int): LocationWeatherDTO
}
