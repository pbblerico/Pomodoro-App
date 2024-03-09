package com.example.alarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.alarmapp.utils.Actions

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Actions.START_TIMER.action -> {
                val intent = Intent(context, TimerService::class.java)
                intent.setAction(Actions.START_TIMER.action)
                context?.startService(intent)
            }
            Actions.STOP_TIMER.action -> {
                val intent = Intent(context, TimerService::class.java)
                intent.setAction(Actions.STOP_TIMER.action)
                context?.startService(intent)
            }
        }
    }
}