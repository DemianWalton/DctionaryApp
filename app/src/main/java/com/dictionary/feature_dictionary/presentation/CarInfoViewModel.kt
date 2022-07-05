package com.dictionary.feature_dictionary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dictionary.core.util.CarState
import com.dictionary.core.util.DataEvent
import com.dictionary.feature_dictionary.data.local.entity.Car
import com.dictionary.feature_dictionary.domain.repository.CarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarInfoViewModel @Inject constructor(
    private val repository: CarsRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow<CarState>(CarState.Empty)
    val searchCar: StateFlow<CarState> = _searchQuery

    private var searchJob: Job? = null

    fun onInsertCar(query: Car) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            repository.insertCars(query)
                .onEach { result ->
                    when (result) {
                        is DataEvent.Success -> {
                            _searchQuery.value = CarState.Success(result.data)
                        }
                        is DataEvent.Failure -> {
                            _searchQuery.value = CarState.Failure(result.data)
                        }
                        is DataEvent.Loading -> {
                            _searchQuery.value = CarState.Loading(result.data)
                        }
                    }
                }.launchIn(this)
        }
    }


    /*fun insertCar(car: Car) {
            repository.insertCars(car)
    }*/
}