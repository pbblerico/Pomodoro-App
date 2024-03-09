package com.example.alarmapp

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Vibrator
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarmapp.utils.Actions
import com.example.alarmapp.utils.Extras
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class TimerService : Service() {
    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    @Inject
    lateinit var countDownTimer: CustomCountDownTimer

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder
    private val serviceScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                Actions.START_TIMER.action -> {
                    Log.d("timer", "${countDownTimer.getTimeLeft()}")
                    countDownTimer.setTime(
                        it.getIntExtra(
                            Extras.SET_TIMER.key,
                            countDownTimer.getTimeLeft()
                        )
                    )
                    startTimer(countDownTimer.getTimeLeft())
                }

                Actions.STOP_TIMER.action -> {
                    Log.d("timer", "stop")
                    countDownTimer.stop()
                }

                Actions.RESET_TIMER.action -> {
                    countDownTimer.reset()
                    updateNotification(formatTime(countDownTimer.getTimeLeft()))
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun onTick() {
        val timeRemaining = formatTime(countDownTimer.getTimeLeft())
        updateNotification(timeRemaining)
    }

    private fun onFinish() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.positive_notification)
        val vibrate = getSystemService(VIBRATOR_SERVICE) as Vibrator

        mediaPlayer.start()
        vibrate.vibrate(500)
    }


    private fun startTimer(seconds: Int) {
        countDownTimer.setTime(seconds)
        serviceScope.launch {
            countDownTimer.tick(
                this,
                onTick = { onTick() },
                onFinish = { onFinish() }
            )
        }
    }

    private fun stopNotification(): PendingIntent {
        val intentStop = Intent(this, MyReceiver::class.java).apply {
            this.action = Actions.STOP_TIMER.action
        }
        return PendingIntent.getBroadcast(
            this, 0, intentStop, PendingIntent.FLAG_MUTABLE
        )
    }

    private fun startNotification(): PendingIntent {
        val intentStart = Intent(this, MyReceiver::class.java).apply {
            this.action = Actions.START_TIMER.action
        }
        return PendingIntent.getBroadcast(
            this, 0, intentStart, PendingIntent.FLAG_MUTABLE
        )
    }

    //todo fix reset
    private fun resetNotification(): PendingIntent {
        val intentStart = Intent(this, MyReceiver::class.java).apply {
            this.action = Actions.RESET_TIMER.action
        }
        return PendingIntent.getBroadcast(
            this, 0, intentStart, PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun updateNotification(contentText: String) {
        notificationBuilder.setContentText(contentText)
            .addAction(
                0,
                "Stop",
                stopNotification()
            )
            .addAction(
                0,
                "Start",
                startNotification()
            )
            .addAction(
                0,
                "Reset",
                resetNotification()
            )

        val notification = notificationBuilder.build()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, notification)
    }

    private fun formatTime(seconds: Int): String {
        val hours = seconds / (60 * 60)
        val minutes = (seconds / 60) % 60
        val secs = seconds % 60 % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


}