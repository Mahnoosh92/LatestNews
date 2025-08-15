package com.mahnoosh.network.data

import com.mahnoosh.network.BuildConfig
import com.mahnoosh.network.data.model.HeadlineDTO
import com.mahnoosh.network.data.model.HeadlineResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String = "general",
        @Query("max") max: Int = 10,
        @Query("lang") lang: String = "en",
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ): Response<HeadlineResponseDTO>

    @GET("top-headlines")
    suspend fun searchHeadlines(
        @Query("q") q: String,
        @Query("max") max: Int = 10,
        @Query("lang") lang: String = "en",
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ): Response<HeadlineResponseDTO>
}
