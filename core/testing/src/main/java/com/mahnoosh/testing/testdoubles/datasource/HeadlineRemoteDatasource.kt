package com.mahnoosh.testing.testdoubles.datasource

import com.mahnoosh.network.data.model.HeadlineDTO
import com.mahnoosh.network.data.remotedatasource.HeadlineRemoteDatasource

class HeadlineRemoteDatasourceTest : HeadlineRemoteDatasource {

    val sampleHeadlines = listOf(
        HeadlineDTO(
            id = "1",
            title = "sample1",
            description = null,
            content = null,
            url = null,
            image = null,
            publishedAt = null
        ),
        HeadlineDTO(
            id = "2",
            title = "sample2",
            description = null,
            content = null,
            url = null,
            image = null,
            publishedAt = null
        )
    )

    override suspend fun getTopHeadlines(category: String): Result<List<HeadlineDTO>> = Result.success(sampleHeadlines)

    override suspend fun searchHeadlines(q: String): Result<List<HeadlineDTO>> = Result.success(sampleHeadlines)
}