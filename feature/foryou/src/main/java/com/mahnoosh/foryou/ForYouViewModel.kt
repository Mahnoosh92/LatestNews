package com.mahnoosh.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.common.Dispatcher
import com.mahnoosh.common.NewsDispatchers
import com.mahnoosh.data.model.Headline
import com.mahnoosh.data.repository.HeadlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForYouViewModel @Inject constructor(
    val headlineRepository: HeadlineRepository,
    @Dispatcher(NewsDispatchers.IO) val  ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<ForYouUiState> = MutableStateFlow(ForYouUiState.Loading)
    val uiState: StateFlow<ForYouUiState>
        get() = _uiState.asStateFlow()

    private val handler = CoroutineExceptionHandler { _, exception ->
        _uiState.value = ForYouUiState.Error(message = exception.message ?: "Something went wrong")
    }

    fun getHeadlines(categoryName: String) {
        viewModelScope.launch(handler) {
            headlineRepository
                .getAllHeadlines(update = false, category = categoryName)
                .flowOn(ioDispatcher)
                .catch {
                    _uiState.value =
                        ForYouUiState.Error(message = it.message ?: "Something went wrong")
                }
                .collect {
                    _uiState.value = ForYouUiState.Success(it)
                }
        }
    }
}

sealed interface ForYouUiState {
    data object Loading : ForYouUiState
    data class Success(val data: List<Headline>) : ForYouUiState
    data class Error(val message: String) : ForYouUiState
}