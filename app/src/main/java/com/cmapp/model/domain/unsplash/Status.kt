package com.cmapp.model.domain.unsplash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val status: String,
    @SerialName("approved_on")
    val approvedOn: String? = null,
)