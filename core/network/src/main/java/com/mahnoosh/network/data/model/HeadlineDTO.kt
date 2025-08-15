package com.mahnoosh.network.data.model

import com.google.gson.annotations.SerializedName

data class HeadlineDTO(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
)

data class HeadlineResponseDTO(
    @SerializedName("totalArticles") val totalArticles: Long,
    @SerializedName("articles") val articles: List<HeadlineDTO>
)