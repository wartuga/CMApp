package com.cmapp.model.domain.potterDB

import kotlinx.serialization.Serializable

@Serializable
data class ChapterAttributes (
    val slug: String,
    val order: Int,
    val summary: String,
    val title: String
)