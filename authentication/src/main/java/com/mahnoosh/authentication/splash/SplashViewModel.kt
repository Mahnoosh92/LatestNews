package com.mahnoosh.authentication.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.authentication.splash.UiState.NavigateToLogin
import com.mahnoosh.authentication.splash.UiState.NavigateToOnBoarding
import com.mahnoosh.datastore.repository.UserPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val userPreferenceRepository: UserPreferenceRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500)
            userPreferenceRepository
                .isOnBoardingShownFlow
                .collect {
                    if(it)
                        _uiState.value = NavigateToLogin
                    else
                        _uiState.value = NavigateToOnBoarding
                }
        }
    }
}

sealed interface UiState {
    data object Loading : UiState
    data object NavigateToLogin : UiState
    data object NavigateToOnBoarding : UiState
}