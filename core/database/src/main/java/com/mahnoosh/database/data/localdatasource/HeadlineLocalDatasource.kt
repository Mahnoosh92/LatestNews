package com.mahnoosh.database.data.localdatasource

import com.mahnoosh.database.data.model.HeadlineEntity
import kotlinx.coroutines.flow.Flow

interface HeadlineLocalDatasource {
    suspend fun upsertHeadlines(headlines: List<HeadlineEntity>)
    suspend fun upsertHeadline(headline: HeadlineEntity)
    fun getHeadlineById(headlineId: String): Flow<HeadlineEntity>
    fun getAllHeadlines(): Flow<List<HeadlineEntity>>
}