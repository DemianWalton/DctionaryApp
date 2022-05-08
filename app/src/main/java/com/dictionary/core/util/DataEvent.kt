package com.dictionary.core.util

import com.dictionary.feature_dictionary.domain.model.WordInfo

sealed class DataEvent<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : DataEvent<T>(data)
    class Success<T>(data: T?) : DataEvent<T>(data)
    class Failure<T>(message: String, data: T? = null) : DataEvent<T>(data, message)
}


sealed class DataState {
    class Success(val result: List<WordInfo>) : DataState()
    class Failure(val result: List<WordInfo>) : DataState()
    class Loading(val result: List<WordInfo>) : DataState()
    object Empty : DataState()
}
