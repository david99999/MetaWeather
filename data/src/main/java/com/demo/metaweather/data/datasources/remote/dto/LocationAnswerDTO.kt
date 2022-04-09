package com.demo.metaweather.data.datasources.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationAnswerDTO(
    @SerialName("title")
    val title: String? = null,

    @SerialName("location_type")
    val locationType: String? = null,

    @SerialName("woeid")
    val whereOnEarthId: Int? = null,

    @SerialName("latt_long")
    val latLong: String? = null

)