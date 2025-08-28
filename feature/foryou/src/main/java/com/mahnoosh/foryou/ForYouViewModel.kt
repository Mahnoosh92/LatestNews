package com.mahnoosh.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.common.Dispatcher
import com.mahnoosh.common.NewsDispatchers
import com.mahnoosh.data.model.Headline
import com.mahnoosh.data.repository.HeadlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class ForYouViewModel @Inject constructor(
    val headlineRepository: HeadlineRepository,
    @Dispatcher(NewsDispatchers.IO) val  ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _categoryName = MutableStateFlow<String?>(null)

    val uiState: StateFlow<ForYouUiState> = _categoryName
        .filterNotNull()
        .flatMapLatest { categoryName ->
            headlineRepository
                .getAllHeadlines(update = false, category = categoryName)
                .map<List<Headline>, ForYouUiState>(ForYouUiState::Success)
                .flowOn(ioDispatcher)
                .catch { exception ->
                    emit(ForYouUiState.Error(message = exception.message ?: "Something went wrong"))
                }
                .onStart { emit(ForYouUiState.Loading) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ForYouUiState.Loading
        )

    fun getHeadlines(categoryName: String) {
        _categoryName.value = categoryName
    }
}
sealed interface ForYouUiState {
    data object Loading : ForYouUiState
    data class Success(val data: List<Headline>) : ForYouUiState
    data class Error(val message: String) : ForYouUiState
}