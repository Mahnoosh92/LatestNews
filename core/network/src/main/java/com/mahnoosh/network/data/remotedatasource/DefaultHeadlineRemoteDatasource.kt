package com.mahnoosh.network.data.remotedatasource

import com.mahnoosh.network.data.ApiService
import com.mahnoosh.network.data.model.HeadlineDTO
import com.mahnoosh.network.data.model.NewsApiException
import com.mahnoosh.network.data.model.NewsError
import com.mahnoosh.network.utils.extensions.toErrorBody
import javax.inject.Inject

class DefaultHeadlineRemoteDatasource @Inject constructor(private val apiService: ApiService) :
    HeadlineRemoteDatasource {
    override suspend fun getTopHeadlines(category: String): Result<List<HeadlineDTO>> {
        val response = apiService.getTopHeadlines(category = category)
        if (response.isSuccessful)
            return Result.success<List<HeadlineDTO>>(response.body()?.articles ?: emptyList())
        else {
            val newsError = response.toErrorBody(NewsError::class.java)
            return Result.failure(NewsApiException(
                newsError?.errors?.get(0) ?: "Something went wrong",
            ))
        }

    }

    override suspend fun searchHeadlines(q: String): Result<List<HeadlineDTO>> {
        val response = apiService.searchHeadlines(q = q)
        if (response.isSuccessful)
            return Result.success<List<HeadlineDTO>>(response.body()?.articles ?: emptyList())
        else {
            val newsError = response.toErrorBody(NewsError::class.java)
            return Result.failure(NewsApiException(newsError?.errors?.get(0) ?: "Something went wrong"))
        }
    }
}