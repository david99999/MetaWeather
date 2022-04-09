package com.demo.metaweather.data.datasources.remote.impl.network

import com.demo.metaweather.data.datasources.remote.dto.LocationAnswerDTO
import com.demo.metaweather.data.datasources.remote.dto.LocationWeatherDTO
import com.demo.metaweather.data.datasources.remote.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Single endpoint to be consumed when fetching Metaweather information
 */
interface MetaWeatherNetworkApi {

    @GET(Constants.LOCATION_SEARCH)
    suspend fun searchLocationsByName(@Query(Constants.PARAM_QUERY) locationName: String): List<LocationAnswerDTO>

    @GET(Constants.LOCATION_WEATHER)
    suspend fun getWeatherForLocation(@Path(Constants.WHERE_ON_EARTH_ID) whereOnEarthId: Int): LocationWeatherDTO
}
