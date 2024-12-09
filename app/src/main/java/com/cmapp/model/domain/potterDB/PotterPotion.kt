package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PotterPotion (
    val id: String,
    val type: String,
    val attributes: PotionAttributes,
    val links: SelfUri
)