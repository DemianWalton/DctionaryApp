package com.dictionary.feature_dictionary.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Component",
    foreignKeys = [ForeignKey(
        entity = CarMetadata::class,
        parentColumns = arrayOf("id"), // The name of the CarMetadata ID field
        childColumns = arrayOf("carId"), // The name of the Component's car ID field
        onDelete = ForeignKey.CASCADE
    )]
)
data class ComponentEntity(
    @ColumnInfo(name = "carId", index = true)
    var carId: Int,

    @PrimaryKey
    @ColumnInfo(name = "componentId")
    var componentId: Int,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "created")
    var created: String,


)