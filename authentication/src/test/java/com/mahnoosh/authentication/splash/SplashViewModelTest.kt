package com.mahnoosh.authentication.splash

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class SplashViewModelTest {

    lateinit var vm: SplashViewModel
    private val testDispatcher = StandardTestDispatcher()
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = SplashViewModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        /*NO_OP*/
        Dispatchers.resetMain()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUiStateOrders() = runTest(testDispatcher.scheduler){
        Assert.assertEquals(vm.uiState.value, UiState.Loading)

        advanceUntilIdle()

        Assert.assertEquals(vm.uiState.value, UiState.Success)
    }
}