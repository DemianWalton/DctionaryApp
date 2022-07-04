package com.dictionary.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dictionary.feature_dictionary.data.local.entity.CarMetadata

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(cars: CarMetadata): Long

    @Query("SELECT * FROM cars WHERE id=:id")
    fun getCar(id: Int): CarMetadata

    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<CarMetadata>

}