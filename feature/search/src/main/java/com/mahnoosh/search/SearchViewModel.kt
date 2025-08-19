package com.mahnoosh.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {
    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    companion object {
        val SEARCH_QUERY = "search_query"
    }

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }
}