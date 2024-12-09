package com.cmapp.model.domain.unsplash

import kotlinx.serialization.Serializable

@Serializable
data class UserLinks(
    val self: String,
    val html: String,
    val photos: String,
    val likes: String,
    val portfolio: String,
    val following: String,
    val followers: String
)