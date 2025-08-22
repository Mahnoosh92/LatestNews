package com.mahnoosh.testing.testdoubles.datasource

import com.mahnoosh.database.data.localdatasource.HeadlineLocalDatasource
import com.mahnoosh.database.data.model.HeadlineEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class HeadlineLocalDatasourceTest : HeadlineLocalDatasource {

    private val localData = MutableStateFlow(emptyList<HeadlineEntity>())

    override suspend fun upsertHeadlines(headlines: List<HeadlineEntity>) {
        localData.update {
            (it+headlines)
                .distinctBy(HeadlineEntity::id)
        }
    }

    override suspend fun upsertHeadline(headline: HeadlineEntity) {
        localData.update {
            (it+listOf(headline))
                .distinctBy(HeadlineEntity::id)
        }
    }

    override fun getHeadlineById(headlineId: String): Flow<HeadlineEntity> {
        return localData
            .map { headlines->
                headlines.find { it.id==headlineId }!!
            }
    }

    override fun getAllHeadlines(): Flow<List<HeadlineEntity>> = localData
}