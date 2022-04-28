package com.dictionary.feature_dictionary.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.dictionary.feature_dictionary.data.local.entity.WordInfoEntity
import com.dictionary.feature_dictionary.domain.model.WordInfo

@Dao
interface WordInfoDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertWord(infos: List<WordInfo>)

    @Query("DELETE FROM wordinfoentity WHERE word IN(:words)")
    suspend fun deleteWord(words: List<String>)

    @Query("SELECT * FROM wordinfoentity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWord(word: String): List<WordInfoEntity>


}