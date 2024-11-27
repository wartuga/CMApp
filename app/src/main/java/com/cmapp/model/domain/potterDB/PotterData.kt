package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PotterData<T> (
    val data: T,
    val meta: Meta,
    val links: Links
)

@Serializable
data class Meta (
    val pagination: Pagination? = null,
    val copyright: String,
    @SerialName(value = "generated_at")
    val generatedAt: String
)

@Serializable
data class Pagination (
    val current: Int,
    val first: Int? = 1,
    val prev: Int? = -1,
    val next: Int? = 1,
    val last: Int? = 1,
    val records: Int
)

@Serializable
data class Links (
    val self: String,
    val current: String? = "",
    val first: String? = "",
    val prev: String? = "",
    val next: String? = "",
    val last: String? = ""
)