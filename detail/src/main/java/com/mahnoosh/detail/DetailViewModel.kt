package com.mahnoosh.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.data.repository.HeadlineRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted val headlineId: String,
    val headlineRepository: HeadlineRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            topicId: String,
        ): DetailViewModel
    }

    val headLine = headlineRepository.getHeadlineById(headlineId = headlineId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )
}