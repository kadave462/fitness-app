package com.example.myfitnessapp.viewModel.utils

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
        fun formatTime(seconds: Int): String {
            val minutes = seconds / 60
            val secs = seconds % 60
            return String.format("%02d:%02d", minutes, secs)
        }
    }
}