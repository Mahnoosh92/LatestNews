package com.mahnoosh.authentication.splash

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mahnoosh.datastore.repository.UserPreferenceRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SplashViewModelTest {

    lateinit var vm: SplashViewModel
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var userPreferenceRepository: UserPreferenceRepository


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        vm = SplashViewModel(userPreferenceRepository = userPreferenceRepository)
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
        every { userPreferenceRepository.isOnBoardingShownFlow } answers {flowOf(true)}
        advanceUntilIdle()

        Assert.assertEquals(vm.uiState.value, UiState.NavigateToLogin)
    }
}
