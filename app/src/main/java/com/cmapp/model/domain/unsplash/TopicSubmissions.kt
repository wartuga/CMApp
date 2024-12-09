package com.cmapp.model.domain.unsplash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicSubmissions(
    val interiors: Status? = null,
    @SerialName("deck-the-halls")
    val deckTheHalls: Status? = null,
    @SerialName("cozy-moments")
    val cozyMoments: Status? = null,
    val holidays: Status? = null,
    val spirituality: Status? = null,
    @SerialName("fashion-beauty")
    val fashionBeauty: Status? = null,
    val people: Status? = null,
    val experimental: Status? = null,
    val wallpapers: Status? = null,
    val film: Status? = null,
    val nature: Status? = null,
    @SerialName("color-theory")
    val colorTheory: Status? = null,
)