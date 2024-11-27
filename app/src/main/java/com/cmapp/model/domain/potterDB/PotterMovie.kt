package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PotterMovie (
    val id: String,
    val type: String,
    val attributes: MovieAttributes,
    val links: SelfUri
)

@Serializable
data class MovieAttributes (
    val slug: String,
    val box_office: String,
    val budget: String,
    val cinematographers: List<String>,
    val directors: List<String>,
    val distributors: List<String>,
    val editors: List<String>,
    @SerialName(value = "music_composers")
    val musicComposers: List<String>,
    val poster: String,
    val producers: List<String>,
    val rating: String,
    @SerialName(value = "release_date")
    val releaseDate: String,
    @SerialName(value = "running_time")
    val runningTime: String,
    val screenwriters: List<String>,
    val summary: String,
    val title: String,
    val trailer: String,
    val wiki: String,
)