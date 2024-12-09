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