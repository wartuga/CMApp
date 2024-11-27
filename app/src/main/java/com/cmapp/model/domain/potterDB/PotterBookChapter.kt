package com.cmapp.model.domain.potterDB

import kotlinx.serialization.Serializable

@Serializable
data class PotterBookChapter (
    val id: String,
    val type: String,
    val attributes: ChapterAttributes,
    val relationships: Book,
    val links: SelfUri
)

@Serializable
data class ChapterAttributes (
    val slug: String,
    val order: Int,
    val summary: String,
    val title: String
)

@Serializable
data class Book (
    val book: BooksData
)

@Serializable
data class BooksData (
    val data: DataInfo
)

@Serializable
data class DataInfo (
    val id: String,
    val type: String
)