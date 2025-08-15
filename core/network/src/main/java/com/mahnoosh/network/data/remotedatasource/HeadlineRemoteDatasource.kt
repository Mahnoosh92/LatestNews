package com.mahnoosh.network.data.remotedatasource

import com.mahnoosh.network.data.model.HeadlineDTO

interface HeadlineRemoteDatasource {
    suspend fun getTopHeadlines(
        category: String
    ): Result<List<HeadlineDTO>>

    suspend fun searchHeadlines(
        q: String
    ): Result<List<HeadlineDTO>>
}