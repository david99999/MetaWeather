package com.demo.metaweather.data.datasources.remote.utils

object Constants {
    const val PARAM_QUERY = "query"
    const val WHERE_ON_EARTH_ID = "WHERE_ON_EARTH_ID"

    private const val WEATHER_SERVER_BASE_URL = "https://www.metaweather.com/"
    const val WEATHER_SERVER_API = WEATHER_SERVER_BASE_URL + "api/"

    const val LOCATION_WEATHER = "location/{$WHERE_ON_EARTH_ID}"
    const val LOCATION_SEARCH = "location/search/"
    private const val WEATHER_ICON_PREFIX = WEATHER_SERVER_BASE_URL + "static/img/weather/"

    fun weatherIcon(weatherState: String) = "$WEATHER_ICON_PREFIX$weatherState.svg"
}
