package com.dictionary.feature_dictionary.domain.repository

import com.dictionary.core.util.DataEvent
import com.dictionary.feature_dictionary.data.local.entity.CarMetadata
import kotlinx.coroutines.flow.Flow

interface CarsRepository {

    //fun insertCars(cars: Car)

    fun insertCars(cars: CarMetadata): Flow<DataEvent<CarMetadata>>

    //suspend fun getCars(word: String): Flow<DataEvent<List<Car>>>


}