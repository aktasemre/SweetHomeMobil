package com.sweethome.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "check_items")
data class CheckItemEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val photoUrl: String,
    val timestamp: Long,
    val location: String?
) 