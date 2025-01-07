package com.sweethome.data.dao

import androidx.room.*
import com.sweethome.data.entity.CheckItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckItemDao {
    @Query("SELECT * FROM check_items ORDER BY timestamp DESC")
    fun getAllCheckItems(): Flow<List<CheckItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckItem(checkItem: CheckItemEntity)

    @Delete
    suspend fun deleteCheckItem(checkItem: CheckItemEntity)
} 