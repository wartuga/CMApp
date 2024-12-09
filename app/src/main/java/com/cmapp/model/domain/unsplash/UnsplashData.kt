package com.cmapp.model.domain.unsplash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashData(
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    val results: List<Image>
)