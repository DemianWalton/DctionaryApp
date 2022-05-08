package com.dictionary.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.dictionary.feature_dictionary.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertWord(infos: List<WordInfoEntity>)

    @Query("DELETE FROM WordInfoEntity WHERE word IN(:words)")
    suspend fun deleteWord(words: List<String>)

    @Query("SELECT * FROM WordInfoEntity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWord(word: String): List<WordInfoEntity>

    @Transaction
    suspend fun insertWordOnSuccessApiResponse(
        words: List<String>,
        infoList: List<WordInfoEntity>
    ) {
        deleteWord(words)
        insertWord(infoList)
    }


}