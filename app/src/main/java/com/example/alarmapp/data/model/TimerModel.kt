package com.example.alarmapp.data.model

import com.example.alarmapp.utils.TimerMode

data class TimerModel(
    val focusTime: Long,
    val shortBreak: Long,
    val longBreak: Long,
    val curMode: TimerMode,
    val timeLeft: Long = 0
)