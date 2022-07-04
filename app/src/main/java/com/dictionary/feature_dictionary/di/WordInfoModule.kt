package com.dictionary.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.dictionary.feature_dictionary.data.local.ComponentConverter
import com.dictionary.feature_dictionary.data.local.WordsDatabase
import com.dictionary.feature_dictionary.data.local.MeaningConverter
import com.dictionary.feature_dictionary.data.remote.DictionaryApi
import com.dictionary.feature_dictionary.data.repository.WordInfoRepositoryImp
import com.dictionary.feature_dictionary.data.util.GsonParser
import com.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideWordInfoRepository(api: DictionaryApi, db: WordsDatabase): WordInfoRepository {
        return WordInfoRepositoryImp(api, db.dao,db.daoCars)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordsDatabase {
        return Room.databaseBuilder(
            app,
            WordsDatabase::class.java,
            "word_db"
        )
            .addTypeConverter(MeaningConverter(GsonParser(Gson())))
            .addTypeConverter(ComponentConverter(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}