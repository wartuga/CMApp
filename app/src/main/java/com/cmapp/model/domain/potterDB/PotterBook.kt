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