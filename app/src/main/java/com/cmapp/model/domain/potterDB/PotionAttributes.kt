package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PotionAttributes (
    val slug: String,
    val characteristics: String? = null,
    val difficulty: String? = null,
    val effect: String? = null,
    val image: String? = null,
    val inventors: String? = null,
    val ingredients: String? = null,
    val manufacturers: String? = null,
    val name: String,
    @SerialName(value = "side_effects")
    val sideEffects: String? = null,
    val time: String? = null,
    val wiki: String,
)