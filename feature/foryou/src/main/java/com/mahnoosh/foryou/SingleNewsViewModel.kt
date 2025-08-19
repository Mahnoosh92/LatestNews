package com.mahnoosh.foryou


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = SingleNewsViewModel.Factory::class)
class SingleNewsViewModel @AssistedInject constructor(@Assisted val topicId: String,): ViewModel() {

    val test =  mutableStateOf(topicId)
    @AssistedFactory
    interface Factory {
        fun create(
            topicId: String,
        ): SingleNewsViewModel
    }
}