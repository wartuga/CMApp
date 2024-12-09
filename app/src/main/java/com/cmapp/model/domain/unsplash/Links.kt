package com.cmapp.model.domain.unsplash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val self: String,
    val html: String,
    val download: String,
    @SerialName("download_location")
    val downloadLocation: String
)