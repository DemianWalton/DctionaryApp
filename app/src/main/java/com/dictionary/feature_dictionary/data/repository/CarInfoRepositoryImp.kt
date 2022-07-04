package com.dictionary.feature_dictionary.data.repository

import com.dictionary.core.util.DataEvent
import com.dictionary.feature_dictionary.data.local.CarDao
import com.dictionary.feature_dictionary.data.local.entity.CarMetadata
import com.dictionary.feature_dictionary.domain.repository.CarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CarInfoRepositoryImp(
    private val dao: CarDao
) : CarsRepository {

    override fun insertCars(cars: CarMetadata): Flow<DataEvent<CarMetadata>> = flow {
            emit(DataEvent.Loading())
            val insertCar = dao.insertCar(cars)

            val car = dao.getCar(insertCar.toInt())

            emit(DataEvent.Success(car))
        }


    }

    /*override fun insertCars(cars: Car) {
        dao.insertCar(cars)
    }*/

    /*override suspend fun getCars(word: String): Flow<DataEvent<List<Car>>> = flow {
        emit(DataEvent.Loading())

        val cars = dao.getAllCars()
        emit(DataEvent.Success(cars))

    }*/
