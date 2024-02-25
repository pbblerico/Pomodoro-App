package com.example.alarmapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alarmapp.data.repository.SharedPrefRepository
import com.example.alarmapp.utils.TimerMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TimerViewModel @Inject constructor(repo: SharedPrefRepository): ViewModel() {
    private val _focusTime = MutableLiveData<Long>()
    val focus = _focusTime

    private val _shortBreak = MutableLiveData<Long>()
    val shortBreak = _shortBreak

    private val _longBreak = MutableLiveData<Long>()
    val longBreak = _longBreak

    private val _mlsLeft = MutableLiveData<Long?>()
    val mlsLeftFocus = _mlsLeft

    private val _curMode = MutableLiveData<TimerMode>()
    val curMode = _curMode

    fun setTimers(focusTime: Long, shortBreakTime: Long, longBreakTime: Long) {
        _focusTime.value = focusTime
        _shortBreak.value = shortBreakTime
        _longBreak.value = longBreakTime
    }

    fun saveTimeLeft(
        timeLeft: Long? = null) {
        _mlsLeft.value = timeLeft
    }

    fun setMode(mode: TimerMode) {
        _curMode.value = mode
    }
}