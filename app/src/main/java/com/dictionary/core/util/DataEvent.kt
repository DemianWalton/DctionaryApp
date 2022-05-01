package com.dictionary.core.util

typealias SimpleDataEvent = DataEvent<Unit>

sealed class DataEvent<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : DataEvent<T>(data)
    class Success<T>(data: T?) : DataEvent<T>(data)
    class Failure<T>(message: String, data: T? = null) : DataEvent<T>(data, message)
}