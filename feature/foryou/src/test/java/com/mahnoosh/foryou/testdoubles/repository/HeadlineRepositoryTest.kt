package com.mahnoosh.foryou.testdoubles.repository

import com.mahnoosh.data.model.Headline
import com.mahnoosh.data.repository.HeadlineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class HeadlineRepositoryTest: HeadlineRepository {
    private val data = MutableStateFlow(emptyList<Headline>())


    override suspend fun upsertHeadlines(headline: List<Headline>) {
         data.update {
            (it+headline)
                .distinctBy(Headline::id)
        }
    }

    override suspend fun upsertHeadline(headline: Headline) {
        data.update {
            (it+listOf(headline))
                .distinctBy(Headline::id)
        }
    }

    override fun getHeadlineById(headlineId: String): Flow<Headline> {
        return data
            .map {headlines->
                headlines.find { it.id==headlineId }!!
            }
    }

    override fun getAllHeadlines(
        update: Boolean,
        category: String
    ): Flow<List<Headline>>  = data
}