package com.dictionary.feature_dictionary.data.local

import android.content.Context
import android.database.Cursor
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dictionary.feature_dictionary.data.local.entity.CarMetadata
import com.dictionary.feature_dictionary.data.local.entity.ComponentEntity
import com.dictionary.feature_dictionary.data.local.entity.WordInfoEntity

@Database(
    version = 4,
    entities = [WordInfoEntity::class, CarMetadata::class, ComponentEntity::class]
    //, autoMigrations = [AutoMigration(from = 3, to = 4)], exportSchema = true
)

@TypeConverters(MeaningConverter::class, ComponentConverter::class)
abstract class WordsDatabase : RoomDatabase() {
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
                    .addMigrations(MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                try {
                    // Extract existing components
                    val cursor = database.query("SELECT `id`, `components` FROM `Cars`")
                    val components = cursorToComponents(cursor)

                    // Make new table without components column, SQLite cannot delete columns
                    database.execSQL("CREATE TABLE IF NOT EXISTS `_new_Cars` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `colour` TEXT NOT NULL)")


                    database.execSQL(
                        "INSERT INTO _new_Cars" +
                                "(id, colour) " +
                                "SELECT " +
                                "id, colour" +
                                " FROM Cars"
                    )

                    database.execSQL("DROP TABLE `Cars`")

                    database.execSQL("ALTER TABLE `_new_Cars` RENAME TO `Cars`")

                    // Add new table & index
                    database.execSQL("CREATE TABLE IF NOT EXISTS `Component` (`carId` INTEGER NOT NULL," +
                            " `componentId` INTEGER NOT NULL," +
                            " `type` TEXT NOT NULL," +
                            " `description` TEXT NOT NULL," +
                            " `created` TEXT NOT NULL, PRIMARY KEY(`componentId`)," +
                            " FOREIGN KEY(`carId`) REFERENCES `Cars`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)")


                    database.execSQL("CREATE INDEX IF NOT EXISTS `index_component_carId` ON `Component` (`carId`)")

                    // Insert new components
                    insertComponents(database, components)
                } catch (e: Exception) {
                    // Migration failed, Room will automatically roll back the transaction and retry when DB accessed
                }
            }
        }

        private fun cursorToComponents(cursor: Cursor): List<ComponentEntity> {
            // 1: First get the creation IDs and serialized component lists until there are no more rows
            val carData = arrayListOf<Pair<Int, String>>()
            if (cursor.moveToFirst()) {
                val carIdIndex = cursor.getColumnIndex("id")
                val componentsIndex = cursor.getColumnIndex("components")
                do {
                    carData.add(
                        Pair(
                            cursor.getInt(carIdIndex),
                            cursor.getString(componentsIndex)
                        )
                    )
                } while (cursor.moveToNext())
            }

            // 2: Parse each serialised list of components into objects, and update their carId
            val componentsList = carData.flatMap { carIdAndComponents ->
                ComponentTypeConverter.toComponents(carIdAndComponents.second).onEach {
                    // 3: Update all carIds
                    it.carId = carIdAndComponents.first
                }
            }

            // 4: Return the list of components
            return componentsList
        }

        private fun insertComponents(
            database: SupportSQLiteDatabase,
            components: List<ComponentEntity>
        ) {
            val insertSql = "INSERT INTO " +
                    "Component(carId, componentId, type, description, created) " +
                    "VALUES (?, ?, ?, ?, ?)"
            val insertStatement = database.compileStatement(insertSql)
            components.forEach {
                insertStatement.clearBindings()
                insertStatement.bindLong(1, it.carId.toLong())
                insertStatement.bindLong(2, it.componentId.toLong())
                insertStatement.bindString(3, it.type)
                insertStatement.bindString(4, it.description)
                insertStatement.bindString(5, it.created)
                insertStatement.executeInsert()
            }
        }
    }
}

