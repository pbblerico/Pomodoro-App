package com.example.alarmapp

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CustomCountDownTimer(
    private var secondsLeft: Int = 0,
    private var job: Job? = null,
    private var isRunning: Boolean = false,
) {
    fun tick(
        scope: CoroutineScope,
        onTick: (() -> Unit?)? = null,
        onFinish: (() -> Unit?)? = null
    ) {
        if (isRunning) return

        isRunning = true
        job = scope.launch {
            while (secondsLeft > 0 && isRunning) {
                Log.d("timer", "$secondsLeft")


                delay(1000)
                secondsLeft--
                onTick?.invoke()
            }
            if(isRunning) {
                Log.d("timer", "$secondsLeft")
                onFinish?.invoke()
                isRunning = false
            }
        }
    }
    fun getTimeLeft(): Int = secondsLeft
    fun setTime(seconds: Int) {
        secondsLeft = seconds
    }

    fun stop() {
        if(secondsLeft > 0) secondsLeft--
        job?.cancel()
        isRunning = false
    }
}