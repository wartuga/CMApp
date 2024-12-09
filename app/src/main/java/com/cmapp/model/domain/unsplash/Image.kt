package com.cmapp.model.domain.unsplash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: String,
    val slug: String,
    @SerialName("alternative_slugs")
    val alternativeSlugs: Slug,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("promoted_at")
    val promotedAt: String? = null,
    val width: Int,
    val height: Int,
    val color: String,
    @SerialName("blur_hash")
    val blurHash: String,
    val description: String? = null,
    @SerialName("alt_description")
    val altDescription: String? = null,
    val breadcrumbs: List<String>,
    val urls: Urls,
    val links: Links,
    val likes: Int,
    @SerialName("liked_by_user")
    val likedByUser: Boolean,
    @SerialName("current_user_collections")
    val currentUserCollections: List<String>,
    val sponsorship: Sponsorship? = null,
    @SerialName("topic_submissions")
    val topicSubmissions: TopicSubmissions,
    @SerialName("asset_type")
    val assetType: String,
    val user: User
)