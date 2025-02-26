package com.example.myfitnessapp.viewmodels.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private var timerJob: Job? = null
    private val _remainingTime = mutableLongStateOf(60_000L)
    val remainingTime: State<Long> = _remainingTime

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    fun toggleTimer() {
        if (_isRunning.value) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        _isRunning.value = true
        timerJob = viewModelScope.launch {
            while (_remainingTime.value > 0) {
                delay(10L)
                _remainingTime.value -= 10
            }
            stopTimer()
        }
    }

    private fun stopTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }
}