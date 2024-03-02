package com.example.alarmapp

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CustomCountDownTimer(
    var secondsLeft: Int,
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
//                job?.cancel()
                this.cancel()
                Log.d("timer", "h")
            }
        }
    }

    fun stop() {
        if(secondsLeft > 0) secondsLeft--
        job?.cancel()
        isRunning = false
    }
}