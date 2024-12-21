package com.samridhi.colorapp.data.network.repository

import com.google.firebase.database.DatabaseReference
import com.samridhi.colorapp.data.local.db.dao.ColorDao
import com.samridhi.colorapp.data.local.db.entity.ColorEntity
import com.samridhi.colorapp.data.network.model.ColorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


class ColorRepository
@Inject constructor(
    private val colorDao: ColorDao,
    private val firebaseDatabase: DatabaseReference
) {
    suspend fun getAllColors(): List<ColorEntity> {
        return colorDao.getAllColors()
    }

    suspend fun getTotalCount(): Int {
        return colorDao.getTotalCount()
    }

    suspend fun getUnsyncedCount(): Int {
        return colorDao.getUnsyncedCount()
    }

    suspend fun addRandomColor() {
        val newColor = ColorEntity(
            id = UUID.randomUUID().toString(),
            colorCode = "#${(0..0xFFFFFF).random().toString(16).padStart(6, '0')}",
            timeStamp = System.currentTimeMillis().toString(),
            synced = false
        )
        colorDao.insertColor(newColor)
    }

    suspend fun syncColors(
        onSuccess: () -> Unit
    ) {
        val unSyncedColors = colorDao.getUnsyncedColors()
        val colorList = unSyncedColors.map {
            ColorData(it.id, it.colorCode, it.timeStamp)
        }
        firebaseDatabase.child("colors").setValue(colorList).addOnSuccessListener {
            onSuccess()
            CoroutineScope(Dispatchers.IO).launch {
                val syncedColors = unSyncedColors.map { it.copy(synced = true) }
                colorDao.updateColors(syncedColors)
            }
        }.addOnFailureListener {
        }
    }
}