package com.dictionary.feature_dictionary.data.local.entity

import androidx.room.*

@Entity(tableName = "Cars")
data class Car(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var colour: String,
    val components: List<Component>
) {}

