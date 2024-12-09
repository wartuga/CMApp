package com.cmapp.model.domain.potterDB

import kotlinx.serialization.Serializable

@Serializable
data class SpellAttributes (
    val slug: String,
    val category: String? = null,
    val creator: String? = null,
    val effect: String? = null,
    val hand: String? = null,
    val image: String? = null,
    val incantation: String? = null,
    val light: String? = null,
    val name: String,
    val wiki: String,
)