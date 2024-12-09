package com.cmapp.model.domain.unsplash

import kotlinx.serialization.Serializable

@Serializable
data class Sponsorship(
    val impressionUrls: List<String>,
    val tagline: String,
    val taglineUrl: String,
    val sponsor: User
)