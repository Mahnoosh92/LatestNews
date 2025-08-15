package com.mahnoosh.data.testdoubles

import com.mahnoosh.database.data.dao.HeadlineDao
import com.mahnoosh.database.data.model.HeadlineEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class HeadlineDaoTest : HeadlineDao {

    private val entitiesStateFlow = MutableStateFlow(emptyList<HeadlineEntity>())

    override suspend fun upsertHeadlines(headlines: List<HeadlineEntity>) {
        entitiesStateFlow.update { oldValues ->
            (headlines + oldValues)
                .distinctBy(HeadlineEntity::id)
                .sortedWith(compareBy(HeadlineEntity::publishedAt))

        }
    }

    override suspend fun upsertHeadline(headline: HeadlineEntity) {
        entitiesStateFlow.update { oldValues ->
            (listOf(headline) + oldValues)
                .distinctBy(HeadlineEntity::id)
                .sortedWith(compareBy(HeadlineEntity::publishedAt))
        }
    }

    override fun getHeadlineById(headlineId: String): Flow<HeadlineEntity> =
        entitiesStateFlow
            .map { list ->
                list.first { it.id == headlineId }
            }

    override fun getAllHeadlines(): Flow<List<HeadlineEntity>> = entitiesStateFlow
}