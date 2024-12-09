package com.cmapp.model.domain.unsplash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Social(
    @SerialName("instagram_username")
    val instagramUsername: String? = null,
    @SerialName("portfolio_url")
    val portfolioUrl: String? = null,
    @SerialName("twitter_username")
    val twitterUsername: String? = null,
    @SerialName("paypal_email")
    val paypalEmail: String? = null
)