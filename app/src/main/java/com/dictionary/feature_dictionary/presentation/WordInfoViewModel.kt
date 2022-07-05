package com.dictionary.feature_dictionary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dictionary.core.util.DataEvent
import com.dictionary.core.util.DataState
import com.dictionary.feature_dictionary.data.local.entity.CarMetadata
import com.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DELAY_TIME = 900L

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val repository: WordInfoRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow<DataState>(DataState.Empty)
    val searchQuery: StateFlow<DataState> = _searchQuery

    private var searchJob: Job? = null

    fun onSearch(query: String/*, car: CarMetadata*/) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            //delay(DELAY_TIME)
            repository.getWord(query/*, car*/)
                .onEach { result ->
                    when (result) {
                        is DataEvent.Success -> {
                            _searchQuery.value = DataState.Success(result.data ?: emptyList())
                        }
                        is DataEvent.Failure -> {
                            _searchQuery.value = DataState.Failure(result.data ?: emptyList())
                        }
                        is DataEvent.Loading -> {
                            _searchQuery.value = DataState.Loading(result.data ?: emptyList())
                        }
                    }
                }.launchIn(this)
        }
    }
}