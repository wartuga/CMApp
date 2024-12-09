package com.cmapp.model.domain.potterDB

import kotlinx.serialization.Serializable

@Serializable
data class Chapter (
    val id: String,
    val type: String
)