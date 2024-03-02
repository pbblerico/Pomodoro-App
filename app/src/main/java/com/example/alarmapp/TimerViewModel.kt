package com.example.alarmapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.data.model.TimerModel
import com.example.alarmapp.data.preferences.PreferencesUtils
import com.example.alarmapp.data.repository.SharedPrefRepository
import com.example.alarmapp.useCase.SharedPrefUseCase
import com.example.alarmapp.utils.TimerMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Timer
import javax.inject.Inject


@HiltViewModel
class TimerViewModel @Inject constructor(
    private val useCase: SharedPrefUseCase
////    private val utils: PreferencesUtils
//    private val repo: SharedPrefRepository
) : ViewModel() {
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

    private val _timer = MutableStateFlow(TimerModel(1000, 1000, 1000, TimerMode.FOCUS))
    val timer = _timer

    init {
        viewModelScope.launch {
            useCase.getTimerModelFlow().collect { timer ->
                _timer.value = timer
                Log.d("viewModel", "$timer")
            }
        }
    }

    fun saveModel() {
        val model =  TimerModel(
            600000, 100000, 100000, TimerMode.FOCUS
        )
        useCase.saveTimerModel(
           model
        )
        _timer.value = model
        Log.d("viewModel", "${timer.value}")
    }

    fun setTimers(focusTime: Long, shortBreakTime: Long, longBreakTime: Long) {
        _focusTime.value = focusTime
        _shortBreak.value = shortBreakTime
        _longBreak.value = longBreakTime
    }

    fun saveTimeLeft(
        timeLeft: Long? = null
    ) {
        _mlsLeft.value = timeLeft
    }

    fun setMode(mode: TimerMode) {
        _curMode.value = mode
    }
}