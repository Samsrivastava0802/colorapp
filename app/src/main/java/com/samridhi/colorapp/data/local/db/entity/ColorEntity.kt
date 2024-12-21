package com.samridhi.colorapp.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color_data")
data class ColorEntity(
   @PrimaryKey val id: String,
    val colorCode: String,
    val timeStamp: String,
    val synced: Boolean
)