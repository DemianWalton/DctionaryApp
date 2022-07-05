package com.dictionary.feature_dictionary.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dictionary.feature_dictionary.data.local.entity.Car
import com.dictionary.feature_dictionary.data.local.entity.WordInfoEntity
import com.dictionary.feature_dictionary.data.util.GsonParser
import com.google.gson.Gson

@Database(
    version = 1,
    entities = [WordInfoEntity::class, Car::class]
)

@TypeConverters(MeaningConverter::class, ComponentConverter::class)
abstract class WordsDatabase : RoomDatabase() {
    val DATABASE_NAME = "word_db"

    abstract val dao: WordInfoDao
    abstract val daoCars: CarDao

    companion object {
        @Volatile
        private var INSTANCE: WordsDatabase? = null

        fun getDatabase(context: Context): WordsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        WordsDatabase::class.java,
                        "word_db"
                    )
                    .addTypeConverter(MeaningConverter(GsonParser(Gson())))
                    .addTypeConverter(ComponentConverter())
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

