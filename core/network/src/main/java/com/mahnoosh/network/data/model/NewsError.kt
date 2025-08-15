package com.mahnoosh.network.data.model

data class NewsError(val errors: List<String>)

data class NewsApiException(
    override val message: String?
) : Exception(message)
