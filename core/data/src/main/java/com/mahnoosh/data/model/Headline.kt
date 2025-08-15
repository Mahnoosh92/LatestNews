package com.mahnoosh.data.model

import com.mahnoosh.database.data.model.HeadlineEntity
import com.mahnoosh.network.data.model.HeadlineDTO

data class Headline(
    val id: String,
    val title: String?,
    val description: String?,
    val content: String?,
    val url: String?,
    val image: String?,
    val publishedAt: String?,
){
    companion object{
        val DEFAULT = Headline(id = "", title = "", description = "", content = "", url = "", image = "", publishedAt = "")
    }
}

fun Headline.toHeadlineEntity() = HeadlineEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    publishedAt = this.publishedAt
)

fun Headline.toHeadlineDTO() = HeadlineDTO(
    id = this.id,
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    publishedAt = this.publishedAt
)

fun HeadlineEntity.toHeadline() = Headline(
    id = this.id,
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    publishedAt = this.publishedAt
)

fun HeadlineDTO.toHeadline() = Headline(
    id = this.id ?: "",
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    publishedAt = this.publishedAt
)
fun HeadlineDTO.toHeadlineEntity() = HeadlineEntity(
    id = this.id ?: "",
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    publishedAt = this.publishedAt
)

