package com.example.alarmapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val _mlsLeftFocus = MutableLiveData<Long?>()
    val mlsLeftFocus = _mlsLeftFocus

    private val _mlsLeftShortBreak = MutableLiveData<Long?>()
    val mlsLeftShortBreak = _mlsLeftShortBreak

    private val _mlsLeftLongBreak = MutableLiveData<Long?>()
    val mlsLeftLongBreak = _mlsLeftLongBreak

    private val _curMode = MutableLiveData<TimerMode>()
    val curMode = _curMode

    fun setTimers(focusTime: Long, shortBreakTime: Long, longBreakTime: Long) {
        _focusTime.value = focusTime
        _shortBreak.value = shortBreakTime
        _longBreak.value = longBreakTime
    }

    fun saveTimeLeft(
        timeLeftFocus: Long? = null,
        timeLeftShortBreak: Long? = null) {
        _mlsLeftFocus.value = timeLeftFocus
    }

    fun setMode(mode: TimerMode) {
        _curMode.value = mode
    }
}