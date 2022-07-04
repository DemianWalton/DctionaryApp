package com.dictionary.feature_dictionary.di

import com.dictionary.feature_dictionary.data.local.WordsDatabase
import com.dictionary.feature_dictionary.data.repository.CarInfoRepositoryImp
import com.dictionary.feature_dictionary.domain.repository.CarsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CarsInfoModule {

    @Provides
    @Singleton
    fun provideCarInfoRepository(db: WordsDatabase): CarsRepository {
        return CarInfoRepositoryImp(db.daoCars)
    }

    /*@Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app,
            Database::class.java,
            "word_db"
        ).addTypeConverter(ComponentConverter(GsonParser(Gson())))
            .build()
    }*/
}