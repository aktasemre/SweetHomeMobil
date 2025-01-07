package com.sweethome.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sweethome.data.dao.CheckItemDao
import com.sweethome.data.entity.CheckItemEntity

@Database(entities = [CheckItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun checkItemDao(): CheckItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 