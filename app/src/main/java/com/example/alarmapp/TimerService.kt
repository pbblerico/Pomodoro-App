package com.example.alarmapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


//@AndroidEntryPoint
class TimerService: Service() {
//    @Inject
    lateinit var notificationManager: NotificationManager
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        startTimer(60000)
    }

    private fun startTimer(millis: Long) {
        val mediaPlayer = MediaPlayer.create(this, R.raw.positive_notification)
        countDownTimer = object: CountDownTimer(millis, 1000) {
            override fun onTick(p0: Long) {
                val timeRemaining = formatTime(p0)
                updateNotification(timeRemaining)
            }

            override fun onFinish() {
                mediaPlayer.start()
                stopSelf()
            }

        }
        countDownTimer.start()
    }

    private fun updateNotification(contentText: String) {
        val intent = Intent(this, MyReceiver::class.java).apply {
            putExtra("MESSAGE", "Clicked!")
            this.action = "cancel_timer"
            countDownTimer.cancel()
        }
        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE
            else
                0
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = "timer_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Timer")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_timer)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(
                0,
                "Stop",
                pendingIntent
            )



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Timer Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = notificationBuilder.build()
        startForeground(1, notification)
    }

    private fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        val hours = (millis / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


}