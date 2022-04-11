package com.demo.metaweather.domain.models

data class LocationWeather(

    val forecasts: List<ConsolidatedWeather>,

    val locationParent: String,

    val title: String
)

data class ConsolidatedWeather(
    val weatherStateName: String,

    val weatherStateAbbr: String,

    val applicableDate: String,

    val minTemp: Double,

    val maxTemp: Double,

    val theTemp: Double
)
