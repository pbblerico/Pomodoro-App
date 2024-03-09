package com.example.alarmapp.data.model

import com.example.alarmapp.utils.TimerMode

data class TimerModel(
    val focusTime: Int,
    val shortBreak: Int,
    val longBreak: Int,
    val curMode: TimerMode,
)