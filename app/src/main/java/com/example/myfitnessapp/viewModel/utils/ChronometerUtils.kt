package com.example.myfitnessapp.viewModel.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChronometerUtils : ViewModel() {
    private var _time = mutableStateOf(0)
    val time: State<Int> = _time

    private var isRunning = mutableStateOf(false)
    private var job: Job? = null

    fun startChronometer() {
        if (!isRunning.value) {
            isRunning.value = true
            job = viewModelScope.launch {
                while (isRunning.value) {
                    delay(1000L)
                    _time.value += 1
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
        _time.value = 0
    }
}