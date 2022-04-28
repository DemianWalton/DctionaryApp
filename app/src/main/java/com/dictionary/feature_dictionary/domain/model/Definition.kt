package com.dictionary.feature_dictionary.domain.model

data class Definition(
    val antonyms: List<Any>,
    val definition: String,
    val synonyms: List<Any>
)