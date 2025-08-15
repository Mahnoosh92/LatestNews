package com.mahnoosh.data.repository

import com.mahnoosh.data.model.Headline
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {
    suspend fun upsertHeadlines(headline: List<Headline>)

    suspend fun upsertHeadline(headline: Headline)

    fun getHeadlineById(headlineId: String): Flow<Headline>

    fun getAllHeadlines(update: Boolean, category: String): Flow<List<Headline>>
}