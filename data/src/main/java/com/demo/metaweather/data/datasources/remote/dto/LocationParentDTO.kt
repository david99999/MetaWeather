package com.demo.metaweather.data.datasources.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationParentDTO(
    @SerialName("title")
    val title: String? = null
)
