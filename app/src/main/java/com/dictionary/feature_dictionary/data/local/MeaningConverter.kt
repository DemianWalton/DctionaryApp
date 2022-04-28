package com.dictionary.feature_dictionary.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.dictionary.feature_dictionary.data.util.JsonParser
import com.dictionary.feature_dictionary.domain.model.Meaning
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class MeaningConverter(private val jsonParser: JsonParser) {

    /**For simplisity of the proyect, instead of making a new table
     * for "meanings" we store it as an string inside the wordInfo table */

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>() {}.type
        ) ?: "[]"
    }


}