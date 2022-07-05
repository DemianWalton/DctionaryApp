package com.dictionary.feature_dictionary.domain.repository

import com.dictionary.core.util.DataEvent
import com.dictionary.feature_dictionary.data.local.entity.Car
import kotlinx.coroutines.flow.Flow

interface CarsRepository {

    //fun insertCars(cars: Car)

    fun insertCars(cars: Car): Flow<DataEvent<Car>>

    //suspend fun getCars(word: String): Flow<DataEvent<List<Car>>>


}