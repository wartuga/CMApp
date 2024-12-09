package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PotterData<T> (
    val data: T,
    val meta: Meta,
    val links: Links
)