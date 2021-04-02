package com.example.locationfinder.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.locationfinder.LocationFinder


/**
 * McDatabase
 */
@Database(
    entities = [
        McItemEntity::class,
    ],
    version = 1
)
abstract class McDatabase : RoomDatabase() {
    abstract fun getDao(): McLocationDao

    companion object {
        private var DB_INSTANCE: McDatabase? = null

        fun getDatabase(): McDatabase {
            val tempInstance = DB_INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    LocationFinder.appContext,
                    McDatabase::class.java,
                    "item_db"
                ).build()

                DB_INSTANCE = instance
                return instance
            }
        }
    }
}