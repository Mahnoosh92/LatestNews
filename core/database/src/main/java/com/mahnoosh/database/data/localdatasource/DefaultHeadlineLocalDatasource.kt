package com.mahnoosh.database.data.localdatasource

import com.mahnoosh.database.data.dao.HeadlineDao
import com.mahnoosh.database.data.model.HeadlineEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultHeadlineLocalDatasource @Inject constructor(private val headlineDao: HeadlineDao):HeadlineLocalDatasource {
    override suspend fun upsertHeadlines(headlines: List<HeadlineEntity>) = headlineDao.upsertHeadlines(headlines = headlines)

    override suspend fun upsertHeadline(headline: HeadlineEntity) = headlineDao.upsertHeadline(headline = headline)

    override fun getHeadlineById(headlineId: String): Flow<HeadlineEntity> = headlineDao.getHeadlineById(headlineId = headlineId)

    override fun getAllHeadlines(): Flow<List<HeadlineEntity>> = headlineDao.getAllHeadlines()
}