package com.cmapp.model.domain.unsplash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    @SerialName("updated_at")
    val updatedAt: String,
    val username: String,
    @SerialName("name")
    val name: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String? = null,
    @SerialName("twitter_username")
    val twitterUsername: String? = null,
    @SerialName("portfolio_url")
    val portfolioUrl: String? = null,
    val bio: String? = null,
    val location: String? = null,
    val links: UserLinks,
    @SerialName("profile_image")
    val profileImage: ProfileImage,
    @SerialName("instagram_username")
    val instagramUsername: String? = null,
    @SerialName("total_collections")
    val totalCollections: Int,
    @SerialName("total_likes")
    val totalLikes: Int,
    @SerialName("total_photos")
    val totalPhotos: Int,
    @SerialName("total_promoted_photos")
    val totalPromotedPhotos: Int,
    @SerialName("total_illustrations")
    val totalIllustrations: Int,
    @SerialName("total_promoted_illustrations")
    val totalPromotedIllustrations: Int,
    @SerialName("accepted_tos")
    val acceptedTos: Boolean,
    @SerialName("for_hire")
    val forHire: Boolean,
    val social: Social
)