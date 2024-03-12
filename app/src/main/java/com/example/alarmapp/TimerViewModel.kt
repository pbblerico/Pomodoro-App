package com.example.alarmapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.data.model.TimerModel
import com.example.alarmapp.useCase.SharedPrefUseCase
import com.example.alarmapp.utils.TimerMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TimerViewModel @Inject constructor(
    private val useCase: SharedPrefUseCase
) : ViewModel() {
    private val _timer = MutableStateFlow(TimerModel(1000, 1000, 1000))
    val timer = _timer
    fun saveModel(focus: Int, shortBreak: Int, longBreak: Int) {
        val model =  TimerModel(
            focus, shortBreak, longBreak
        )
        useCase.saveTimerModel(
           model
        )
        _timer.value = model
    }

    fun getTimerByMode(mode: TimerMode): Int {
        if(mode == TimerMode.FOCUS) return timer.value.focusTime
        else if(mode == TimerMode.SHORT_BREAK) return timer.value.shortBreak
        else return timer.value.longBreak
    }


    fun getModel() {
        viewModelScope.launch {
            useCase.getTimerModelFlow().collect { timer ->
                _timer.value = timer
            }
        }
    }
}