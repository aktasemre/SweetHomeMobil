package com.sweethome.di

import android.content.Context
import com.sweethome.data.AppDatabase
import com.sweethome.data.dao.CheckItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideCheckItemDao(database: AppDatabase): CheckItemDao {
        return database.checkItemDao()
    }
} 