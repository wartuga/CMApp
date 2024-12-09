package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PotterChar (
    val id: String,
    val type: String,
    val attributes: CharAttributes,
    val links: SelfUri
)