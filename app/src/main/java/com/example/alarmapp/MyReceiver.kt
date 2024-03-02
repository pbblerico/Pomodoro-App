package com.example.alarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return

        val action = intent?.action ?: return
//        val serviceIntent = Intent(context, TimerService::class.java)
//
//        when(action) {
//            TimerService
//        }
        when(action) {

        }
        val message = intent?.getStringExtra("MESSAGE")
        if (message != null) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}