package com.dictionary.feature_dictionary.data.remote.dto

import com.dictionary.feature_dictionary.domain.model.Meaning

data class MeaningDto(
    val antonyms: List<Any>,
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String,
    val synonyms: List<String>
) {
    fun toMeaningClass(): Meaning {
        return Meaning(
            antonyms = antonyms,
            definitions = definitions,
            partOfSpeech = partOfSpeech,
            synonyms = synonyms

        )
    }

}