package com.dictionary.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dictionary.feature_dictionary.domain.model.Meaning
import com.dictionary.feature_dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    @PrimaryKey val id: Int,
    val word: String,
    val phonetic: String,
    val meanings: List<Meaning>

) {
    fun toWordInfoClass(): WordInfo {
        return WordInfo(
            word = word,
            phonetic = phonetic,
            meanings = meanings
        )
    }
}
