package com.sweethome.repository

import com.sweethome.data.dao.CheckItemDao
import com.sweethome.data.entity.CheckItemEntity
import com.sweethome.models.CheckItem
import com.sweethome.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckItemRepository @Inject constructor(
    private val checkItemDao: CheckItemDao
) {
    fun getAllCheckItems(): Flow<Result<List<CheckItem>>> = flow {
        try {
            emit(Result.Loading)
            checkItemDao.getAllCheckItems()
                .map { entities -> entities.map { it.toCheckItem() } }
                .collect { items ->
                    emit(Result.Success(items))
                }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun saveCheckItem(checkItem: CheckItem): Result<Unit> = try {
        checkItemDao.insertCheckItem(checkItem.toEntity())
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e)
    }

    suspend fun deleteCheckItem(checkItem: CheckItem): Result<Unit> = try {
        checkItemDao.deleteCheckItem(checkItem.toEntity())
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e)
    }

    private fun CheckItemEntity.toCheckItem() = CheckItem(
        id = id,
        title = title,
        description = description,
        photoUrl = photoUrl,
        timestamp = timestamp,
        location = location
    )

    private fun CheckItem.toEntity() = CheckItemEntity(
        id = id,
        title = title,
        description = description,
        photoUrl = photoUrl,
        timestamp = timestamp,
        location = location
    )
} 