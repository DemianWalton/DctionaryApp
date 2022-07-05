package com.dictionary.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dictionary.feature_dictionary.data.local.entity.Car

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(cars: Car): Long

    @Query("SELECT * FROM cars WHERE id=:id")
    fun getCar(id: Int): Car

    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<Car>

}