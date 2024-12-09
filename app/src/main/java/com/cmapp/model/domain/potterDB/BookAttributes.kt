package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookAttributes (
    val slug: String,
    val author: String,
    val cover: String,
    val dedication: String,
    val pages: Int,
    @SerialName(value = "release_date")
    val releaseDate: String,
    val summary: String,
    val title: String,
    val wiki: String,
)