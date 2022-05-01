package com.dictionary.feature_dictionary.data.repository

import com.dictionary.core.util.DataEvent
import com.dictionary.feature_dictionary.data.local.WordInfoDao
import com.dictionary.feature_dictionary.data.remote.DictionaryApi
import com.dictionary.feature_dictionary.domain.model.WordInfo
import com.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImp(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWord(word: String): Flow<DataEvent<List<WordInfo>>> = flow {
        emit(DataEvent.Loading())

        val wordsInfo = dao.getWord(word).map { it.toWordInfoClass() }
        emit(DataEvent.Loading(wordsInfo))

        try {
            val remoteWordInfo = api.getDefinition(word)
            dao.insertWordOnSuccessApiResponse(
                remoteWordInfo.map { it.word },
                remoteWordInfo.map { it.toWordInfoEntity() })
        } catch (e: HttpException) {
            emit(
                DataEvent.Failure(
                    "Complete error message",
                    wordsInfo
                )
            )
        } catch (e: IOException) {
            emit(
                DataEvent.Failure(
                    "Complete error message(internet connection probably)",
                    wordsInfo
                )
            )
        }

        val newWordsInfo = dao.getWord(word).map { it.toWordInfoClass() }
        emit(DataEvent.Success(newWordsInfo))
    }
}