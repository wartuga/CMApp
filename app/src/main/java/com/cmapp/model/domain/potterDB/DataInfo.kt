package com.cmapp.model.domain.potterDB

import kotlinx.serialization.Serializable

@Serializable
data class DataInfo (
    val id: String,
    val type: String
)