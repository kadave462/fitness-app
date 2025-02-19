package com.example.myfitnessapp.viewmodels.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale

class TimeUtils {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun getCurrentTime(): String {
        return dateFormat.format(calendar.time)
    }

    companion object {
        @SuppressLint("DefaultLocale")
        fun formatTime(milliseconds: Long): String {
            val minutes = (milliseconds / 60000) % 60
            val seconds = (milliseconds / 1000) % 60
            val centiseconds = (milliseconds % 1000) / 10
            return String.format("%02d:%02d:%02d", minutes, seconds, centiseconds)
        }
    }
}