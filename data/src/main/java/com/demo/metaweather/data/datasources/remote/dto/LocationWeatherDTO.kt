package com.demo.metaweather.data.datasources.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationWeatherDTO(

    @SerialName("consolidated_weather")
    val consolidatedWeatherDTO: ArrayList<ConsolidatedWeatherDTO> = arrayListOf(),

    @SerialName("time")
    val time: String? = null,

    @SerialName("sun_rise")
    val sunRise: String? = null,

    @SerialName("sun_set")
    val sunSet: String? = null,

    @SerialName("timezone_name")
    val timezoneName: String? = null,

    @SerialName("parent")
    val locationParentDTO: LocationParentDTO? = LocationParentDTO(),

    @SerialName("sources")
    val sources: ArrayList<SourcesDTO> = arrayListOf(),

    @SerialName("title")
    val title: String? = null,

    @SerialName("location_type")
    val locationType: String? = null,

    @SerialName("woeid")
    val whereOnEarthId: Int? = null,

    @SerialName("latt_long")
    val latLong: String? = null,

    @SerialName("timezone")
    val timezone: String? = null

)
