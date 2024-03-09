package com.example.alarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.alarmapp.utils.Actions

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, TimerService::class.java)
        when(intent?.action) {
            Actions.START_TIMER.action -> {
                serviceIntent.setAction(Actions.START_TIMER.action)
            }
            Actions.STOP_TIMER.action -> {
                serviceIntent.setAction(Actions.STOP_TIMER.action)
            }
            Actions.RESET_TIMER.action -> {
                serviceIntent.setAction(Actions.RESET_TIMER.action)
            }
        }
        context?.startService(serviceIntent)
    }
}