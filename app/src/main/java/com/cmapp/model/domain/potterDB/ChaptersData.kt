package com.cmapp.model.domain.potterDB

import kotlinx.serialization.Serializable

@Serializable
data class ChaptersData (
    val data: List<Chapter>
)