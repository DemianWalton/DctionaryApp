package com.dictionary.feature_dictionary.domain.model

import com.dictionary.feature_dictionary.data.remote.dto.DefinitionDto

data class Meaning(
    val antonyms: List<Any>,
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String,
    val synonyms: List<String>
)
