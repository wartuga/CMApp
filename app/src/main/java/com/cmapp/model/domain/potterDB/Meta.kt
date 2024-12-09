package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta (
    val pagination: Pagination? = null,
    val copyright: String,
    @SerialName(value = "generated_at")
    val generatedAt: String
)