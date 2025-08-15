package com.mahnoosh.data.repository

import com.mahnoosh.common.Dispatcher
import com.mahnoosh.common.NewsDispatchers
import com.mahnoosh.data.model.Headline
import com.mahnoosh.data.model.toHeadline
import com.mahnoosh.data.model.toHeadlineEntity
import com.mahnoosh.database.data.localdatasource.HeadlineLocalDatasource
import com.mahnoosh.database.data.model.HeadlineEntity
import com.mahnoosh.network.data.model.HeadlineDTO
import com.mahnoosh.network.data.remotedatasource.HeadlineRemoteDatasource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultHeadlineRepository @Inject constructor(
    private val headlineLocalDatasource: HeadlineLocalDatasource,
    private val headlineRemoteDatasource: HeadlineRemoteDatasource,
    @Dispatcher(NewsDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : HeadlineRepository {
    override suspend fun upsertHeadlines(headline: List<Headline>) =
        headlineLocalDatasource.upsertHeadlines(
            headlines = headline.map(Headline::toHeadlineEntity)
        )

    override suspend fun upsertHeadline(headline: Headline) =
        headlineLocalDatasource.upsertHeadline(headline = headline.toHeadlineEntity())

    override fun getHeadlineById(headlineId: String): Flow<Headline> =
        headlineLocalDatasource.getHeadlineById(headlineId = headlineId).map(
            HeadlineEntity::toHeadline
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllHeadlines(update: Boolean, category: String): Flow<List<Headline>> {
        if (update) {
            return flow {
                val res = headlineRemoteDatasource.getTopHeadlines(category = category)
                if (res.isSuccess) {
                    res.getOrNull()?.let { list ->
                        headlineLocalDatasource.upsertHeadlines(
                            headlines = list.map(
                                HeadlineDTO::toHeadlineEntity
                            )
                        )
                    }
                } else {
                    throw Exception(res.exceptionOrNull()?.message)
                }
                emitAll(
                    headlineLocalDatasource.getAllHeadlines()
                        .map { it.map(HeadlineEntity::toHeadline) })
            }.flowOn(ioDispatcher)
        } else {
            return headlineLocalDatasource.getAllHeadlines()
                .map { it.map(HeadlineEntity::toHeadline) }
        }
    }

}