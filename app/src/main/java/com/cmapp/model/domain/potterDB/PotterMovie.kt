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