package com.samridhi.colorapp.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtil {
    fun convertMillisToDate(timeMillis: String): String {
        return try {
            val millis = timeMillis.toLong()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = Date(millis)
            dateFormat.format(date)
        } catch (e: Exception) {
            "Invalid input"
        }
    }
}