package com.dictionary.feature_dictionary.data.remote.dto

import com.dictionary.feature_dictionary.domain.model.Phonetic

data class PhoneticDto(
    val audio: String,
    val license: LicenseXDto,
    val sourceUrl: String,
    val text: String
) {
    fun toPhoneticClass(): Phonetic {
        return Phonetic(
            sourceUrl = sourceUrl,
            text = text
        )
    }

}