package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PotterBook (
    val id: String,
    val type: String,
    val attributes: BookAttributes,
    val relationships: Chapters,
    val links: SelfUri
)

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

@Serializable
data class Chapters (
    val chapters: ChaptersData
)

@Serializable
data class ChaptersData (
    val data: List<Chapter>
)

@Serializable
data class Chapter (
    val id: String,
    val type: String
)

@Serializable
data class SelfUri (
    val self: String
)