package com.cmapp.model.domain.potterDB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PotterChar (
    val id: String,
    val type: String,
    val attributes: CharAttributes,
    val links: SelfUri
)

@Serializable
data class CharAttributes (
    val slug: String,
    @SerialName(value = "alias_names")
    val aliasNames: List<String>,
    val animagus: String? = null,
    @SerialName(value = "blood_status")
    val bloodStatus: String? = null,
    val boggart: String? = null,
    val born: String? = null,
    val died: String? = null,
    @SerialName(value = "eye_color")
    val eyeColor: String? = null,
    @SerialName(value = "family_members")
    val familyMembers: List<String>,
    val gender: String? = null,
    @SerialName(value = "hair_color")
    val hairColor: String? = null,
    val height: String? = null,
    val house: String? = null,
    val image: String? = null,
    val jobs: List<String>,
    @SerialName(value = "marital_status")
    val maritalStatus: String? = null,
    val name: String? = null,
    val nationality: String? = null,
    val patronus: String? = null,
    val romances: List<String>,
    @SerialName(value = "skin_color")
    val skin_color: String? = null,
    val species: String? = null,
    val titles: List<String>,
    val wands: List<String>,
    val weight: String? = null,
    val wiki: String? = null,
)