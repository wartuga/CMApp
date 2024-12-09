package com.cmapp.model.domain.unsplash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
    @SerialName("small_s3")
    val smallS3: String? = null,
)