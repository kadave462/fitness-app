package com.example.myfitnessapp.viewmodels.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChronometerUtils : ViewModel() {
    private var _time = mutableLongStateOf(0L)
    val time: State<Long> = _time

    var isRunning = mutableStateOf(false)
    private var job: Job? = null

    fun startChronometer() {
        if (!isRunning.value) {
            isRunning.value = true
            job = viewModelScope.launch {
                while (isRunning.value) {
                    delay(10L)
                    _time.value += 10
                }
            }
        }
    }

    fun stopChronometer() {
        isRunning.value = false
        job?.cancel()
    }

    fun resetChronometer() {
        stopChronometer()
        _time.longValue = 0L
    }
}