package com.dictionary.feature_dictionary.data.remote.dto

import com.dictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoDto(
    val license: LicenseDto,
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val sourceUrls: List<String>,
    val word: String
) {
    fun toWordInfoClass(): WordInfo {
        return WordInfo(
            meanings = meanings.map { it.toMeaningClass() },
            phonetic = phonetic,
            word = word
        )
    }

}