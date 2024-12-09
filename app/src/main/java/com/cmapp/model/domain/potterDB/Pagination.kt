package com.cmapp.model.domain.potterDB

import kotlinx.serialization.Serializable

@Serializable
data class Pagination (
    val current: Int,
    val first: Int? = 1,
    val prev: Int? = -1,
    val next: Int? = 1,
    val last: Int? = 1,
    val records: Int
)