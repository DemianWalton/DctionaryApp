package com.dictionary.feature_dictionary.domain.use_case

import com.dictionary.core.util.DataEvent
import com.dictionary.feature_dictionary.domain.model.WordInfo
import com.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo(
    private val repository: WordInfoRepository
) {

    operator fun invoke(word: String): Flow<DataEvent<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow {}
        }
        return repository.getWord(word )
    }

}