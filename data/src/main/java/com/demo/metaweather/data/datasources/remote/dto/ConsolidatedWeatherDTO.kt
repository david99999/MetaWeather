package com.demo.metaweather.data.datasources.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ConsolidatedWeatherDTO(

    @SerialName("weather_state_name") val weatherStateName: String? = null,

    @SerialName("weather_state_abbr") val weatherStateAbbr: String? = null,

    @SerialName("wind_direction_compass") val windDirectionCompass: String? = null,

    @SerialName("created") val created: String? = null,

    @SerialName("applicable_date") val applicableDate: String? = null,

    @SerialName("min_temp") val minTemp: Double? = null,

    @SerialName("max_temp") val maxTemp: Double? = null,

    @SerialName("the_temp") val theTemp: Double? = null,

    @SerialName("wind_speed") val windSpeed: Double? = null,

    @SerialName("wind_direction") val windDirection: Double? = null,

    @SerialName("air_pressure") val airPressure: Double? = null,

    @SerialName("humidity") val humidity: Double? = null,

    @SerialName("visibility") val visibility: Double? = null,

    @SerialName("predictability") val predictability: Double? = null

)