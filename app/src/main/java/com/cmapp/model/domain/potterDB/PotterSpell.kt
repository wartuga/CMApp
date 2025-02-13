package com.cmapp.model.domain.potterDB

import kotlinx.serialization.Serializable

@Serializable
data class PotterSpell (
    val id: String,
    val type: String,
    val attributes: SpellAttributes,
    val links: SelfUri
)