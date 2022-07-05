package com.dictionary.feature_dictionary.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.dictionary.feature_dictionary.data.local.entity.Component
import com.dictionary.feature_dictionary.data.util.JsonParser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class ComponentConverter(/*private val jsonParser: JsonParser*/) {

    /**For simplisity of the proyect, instead of making a new table
     * for "meanings" we store it as an string inside the wordInfo table */

    @TypeConverter
    fun fromComponentJson(json: String): List<Component> {
        return Gson().fromJson<ArrayList<Component>>(
            json,
            object : TypeToken<ArrayList<Component>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toComponentJson(meanings: List<Component>): String {
        return Gson().toJson(
            meanings,
            object : TypeToken<ArrayList<Component>>() {}.type
        ) ?: "[]"
    }


}