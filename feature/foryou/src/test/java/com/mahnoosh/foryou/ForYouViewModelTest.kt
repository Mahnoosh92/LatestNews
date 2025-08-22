package com.mahnoosh.foryou

import com.mahnoosh.data.model.Headline
import com.mahnoosh.data.repository.HeadlineRepository
import com.mahnoosh.foryou.testdoubles.repository.HeadlineRepositoryTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ForYouViewModelTest {

    lateinit var vm: ForYouViewModel
    lateinit var repository: HeadlineRepository

    val dispatcher = StandardTestDispatcher()
    val sampleHeadlines = listOf(
        Headline(
            id = "1",
            title = "sample1",
            description = null,
            content = null,
            url = null,
            image = null,
            publishedAt = null
        ),
        Headline(
            id = "2",
            title = "sample2",
            description = null,
            content = null,
            url = null,
            image = null,
            publishedAt = null
        )
    )
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = HeadlineRepositoryTest()
        vm = ForYouViewModel(headlineRepository = repository, ioDispatcher = dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun uiStateShouldBeLoadingInitially() = runTest(dispatcher.scheduler) {
        Assert.assertEquals(vm.uiState.value, ForYouUiState.Loading)
    }

    @Test
    fun ioStateShouldBeSuccess() = runTest(dispatcher.scheduler){
        backgroundScope.launch { vm.uiState.collect() }

        repository.upsertHeadlines(sampleHeadlines)
        vm.getHeadlines(categoryName = "global")
        Assert.assertEquals(vm.uiState.value, ForYouUiState.Loading)

        dispatcher.scheduler.advanceUntilIdle()

        Assert.assertEquals(vm.uiState.value, ForYouUiState.Success(sampleHeadlines))

    }
}