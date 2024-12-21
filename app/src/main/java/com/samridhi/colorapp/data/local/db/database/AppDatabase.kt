package com.samridhi.colorapp.data.local.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samridhi.colorapp.data.local.db.dao.ColorDao
import com.samridhi.colorapp.data.local.db.entity.ColorEntity

@Database(entities = [ColorEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao
}