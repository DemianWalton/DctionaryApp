package com.dictionary.feature_dictionary.data.util

import java.lang.reflect.Type

interface JsonParser {
    /**>We use an interface for flexibility if we want to use a different library for json**/
    fun <T> fromJson(json: String, type: Type): T?

    fun <T> toJson(obj: T, type: Type): String?

}