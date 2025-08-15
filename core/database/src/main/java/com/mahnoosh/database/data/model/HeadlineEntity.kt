package com.mahnoosh.database.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "headline")
data class HeadlineEntity(
    @PrimaryKey @NonNull val id: String,
    @ColumnInfo("title") val title: String?,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("content") val content: String?,
    @ColumnInfo("url") val url: String?,
    @ColumnInfo("image") val image: String?,
    @ColumnInfo("publishedAt") val publishedAt: String?,
)
