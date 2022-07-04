package com.dictionary.feature_dictionary.data.local

import androidx.room.TypeConverter
import com.dictionary.feature_dictionary.data.local.entity.ComponentEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class ComponentTypeConverter {

    companion object {

        @TypeConverter
        fun stringToSomeObjectList(data: String?): ComponentEntity? {
            val gson = Gson()
            if (data == null) {
                return null
            }

            return gson.fromJson(data, ComponentEntity::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun toComponents(jsonComponents: String): List<ComponentEntity> {
            val componentsType = object : TypeToken<List<ComponentEntity>>() {}.type
            return Gson().fromJson<List<ComponentEntity>>(jsonComponents, componentsType)
        }

    }
}