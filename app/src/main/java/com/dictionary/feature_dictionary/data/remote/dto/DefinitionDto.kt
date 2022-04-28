package com.dictionary.feature_dictionary.data.remote.dto

import com.dictionary.feature_dictionary.domain.model.Definition

data class DefinitionDto(
    val antonyms: List<Any>,
    val definition: String,
    val synonyms: List<Any>
) {
    fun toDefinitionClass(): Definition {
        return Definition(
            antonyms = antonyms,
            definition = definition,
            synonyms = synonyms

        )
    }
}