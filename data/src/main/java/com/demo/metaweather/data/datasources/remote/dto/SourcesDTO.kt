package com.demo.metaweather.data.datasources.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourcesDTO(

    @SerialName("title")
    val title: String? = null,

    @SerialName("slug")
    val slug: String? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("crawl_rate")
    val crawlRate: Int? = null

)