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
) {
    companion object {
        val DEFAULT = Headline(
            id = "71b99c858e6a70f708b8bc4892834a44",
            title = "Google's Pixel 7 and 7 Pro's design gets revealed even more with fresh crisp renders",
            description = "Now we have a complete image of what the next Google flagship phones will look like. All that's left now is to welcome them during their October announcement!",
            content = "Google's highly anticipated upcoming Pixel 7 series is just around the corner, scheduled to be announced on October 6, 2022, at 10 am EDT during the Made by Google event. Well, not that there is any lack of images showing the two new Google phones, b... [1419 chars]",
            url = "https://www.phonearena.com/news/google-pixel-7-and-pro-design-revealed-even-more-fresh-renders_id142800",
            image = "https://m-cdn.phonearena.com/images/article/142800-wide-two_1200/Googles-Pixel-7-and-7-Pros-design-gets-revealed-even-more-with-fresh-crisp-renders.jpg",
            publishedAt = "2022-09-28T08:14:24Z"
        )
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

