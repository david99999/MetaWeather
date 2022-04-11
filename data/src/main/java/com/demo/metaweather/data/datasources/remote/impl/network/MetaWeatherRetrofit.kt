package com.demo.metaweather.data.datasources.remote.impl.network

import com.demo.metaweather.data.datasources.remote.RemoteWeatherDatasource
import com.demo.metaweather.data.datasources.remote.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

/**
 * Creates the retrofit instance for consuming Metaweather API
 */
object MetaWeatherRetrofit {

    private val jsonContentType = MediaType.get("application/json")
    private val json = Json { ignoreUnknownKeys = true }

    fun getMetaWeatherApi(): RemoteWeatherDatasource {
        return Retrofit.Builder()
            .baseUrl(Constants.WEATHER_SERVER_API)
            .addConverterFactory(json.asConverterFactory(jsonContentType))
            .build()
            .create(MetaWeatherNetworkApi::class.java)
    }
}
