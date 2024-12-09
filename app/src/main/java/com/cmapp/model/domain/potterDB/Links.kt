package com.cmapp.model.domain.potterDB

import kotlinx.serialization.Serializable

@Serializable
data class Links (
    val self: String,
    val current: String? = "",
    val first: String? = "",
    val prev: String? = "",
    val next: String? = "",
    val last: String? = ""
)