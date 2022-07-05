package com.dictionary.feature_dictionary.domain.repository

import com.dictionary.core.util.DataEvent
import com.dictionary.feature_dictionary.data.local.entity.Car
import com.dictionary.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWord(word: String, car: Car): Flow<DataEvent<List<WordInfo>>>
}