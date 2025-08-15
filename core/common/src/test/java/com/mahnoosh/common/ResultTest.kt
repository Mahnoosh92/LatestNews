package com.mahnoosh.common

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ResultTest {
    val message = "Something wrong happened"

    @Test
    fun testResultEmissions() = runTest {
        flow {
            emit(1)
            throw Exception(message)
        }
            .asResult()
            .test {
                assertEquals(awaitItem(), Result.Loading)
                assertEquals(awaitItem(), Result.Success(1))
                val errorResult = awaitItem()
                when (errorResult) {
                    is Result.Error -> {assertEquals(errorResult.exception.message, message)}
                    else -> {}
                }
                awaitComplete()
            }
    }
}