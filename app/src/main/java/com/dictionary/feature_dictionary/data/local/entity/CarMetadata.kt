package com.dictionary.feature_dictionary.data.local.entity

import androidx.room.*

@Entity(tableName = "Cars")
data class CarMetadata(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var colour: String,
    val components: List<Component>
) {}

class Car(
    @Embedded
    var metadata: CarMetadata,

    @Relation(
        parentColumn = "id", // The name of the CarMetadata ID field
        entityColumn = "carId" // The name of the Component's car ID field
    )
    var components: List<ComponentEntity>
)
