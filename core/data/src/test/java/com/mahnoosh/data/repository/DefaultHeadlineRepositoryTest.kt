package com.mahnoosh.data.repository

import app.cash.turbine.test
import com.mahnoosh.data.model.Headline
import com.mahnoosh.data.model.toHeadlineDTO
import com.mahnoosh.data.model.toHeadlineEntity
import com.mahnoosh.database.data.localdatasource.HeadlineLocalDatasource
import com.mahnoosh.network.data.remotedatasource.HeadlineRemoteDatasource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DefaultHeadlineRepositoryTest {

    @MockK
    lateinit var headlineLocalDatasource: HeadlineLocalDatasource

    @MockK
    lateinit var headlineRemoteDatasource: HeadlineRemoteDatasource

    val dispatcher = StandardTestDispatcher()
    lateinit var repository: HeadlineRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = DefaultHeadlineRepository(
            headlineLocalDatasource = headlineLocalDatasource,
            headlineRemoteDatasource = headlineRemoteDatasource,
            ioDispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
    }

    @Test()
    fun verifyUpsertHeadlineIsCalledOnDatasource() = runTest {
        coEvery { headlineLocalDatasource.upsertHeadline(any()) } returns Unit

        repository.upsertHeadline(Headline.DEFAULT)

        coVerify(exactly = 1) { headlineLocalDatasource.upsertHeadline(any()) }
    }

    @Test
    fun verifyGetHeadlineByIdReturnsCorrectValue() = runTest {
        coEvery { headlineLocalDatasource.getHeadlineById(any()) } returns flowOf(Headline.DEFAULT.toHeadlineEntity())

        val result = repository.getHeadlineById(Headline.DEFAULT.id)

        coVerify(exactly = 1) { headlineLocalDatasource.getHeadlineById(any()) }
        result.test {
            Assert.assertEquals(awaitItem(), Headline.DEFAULT)
            awaitComplete()
        }
    }

    @Test
    fun verifyGetAllHeadlinesWithUpdateFlagAndSuccessResult() = runTest(dispatcher) {
        coEvery { headlineRemoteDatasource.getTopHeadlines(any()) } returns Result.success(
            listOf(
                Headline.DEFAULT.toHeadlineDTO()
            )
        )

        coEvery { headlineLocalDatasource.upsertHeadlines(any()) } returns Unit
        coEvery { headlineLocalDatasource.getAllHeadlines() } returns flowOf(listOf(Headline.DEFAULT.toHeadlineEntity()))

        val actualResult = repository.getAllHeadlines(update = true, category = "")

        actualResult.test {
            Assert.assertEquals(awaitItem(), listOf(Headline.DEFAULT))
            awaitComplete()
        }
    }

    @Test
    fun verifyGetAllHeadlinesWithUpdateFlagAndFailureResult() = runTest(dispatcher) {
        val exceptionMessage = "something went wrong"
        coEvery { headlineRemoteDatasource.getTopHeadlines(any()) } returns Result.failure(
            Exception(exceptionMessage)
        )

        val actualResult = repository.getAllHeadlines(update = true, category = "")

        actualResult.test {
            Assert.assertEquals(awaitError().message, exceptionMessage)
        }
    }

}