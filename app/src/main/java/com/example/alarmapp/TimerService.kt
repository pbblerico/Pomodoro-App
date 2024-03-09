package com.example.alarmapp

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.Vibrator
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarmapp.utils.Actions
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
    private var active = false
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action) {
                Actions.START_TIMER.action -> {
                    Log.d("timer", "${countDownTimer.getTimeLeft()}")
                    startTimer(countDownTimer.getTimeLeft())
                    active = true
                    countDownTimer.setTime(it.getIntExtra(Actions.SET_TIMER.action, countDownTimer.getTimeLeft()))
                }

                Actions.STOP_TIMER.action -> {
                    Log.d("timer", "stop")
                    countDownTimer.stop()
                    active = false
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun onTick() {
        val timeRemaining = formatTime(countDownTimer.getTimeLeft())
        updateActiveNotification(timeRemaining)
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

    private fun updateActiveNotification(contextText: String) {
        val intentStop = Intent(this, MyReceiver::class.java).apply {
            this.action = Actions.STOP_TIMER.action
        }
        updateNotification(intentStop, contextText)
    }

    private fun updateStoppedNotification(contextText: String) {
        val intentStart = Intent(this, MyReceiver::class.java).apply {
            this.action = Actions.START_TIMER.action
        }
        updateNotification(intentStart, contextText)
    }

    private fun updateNotification(intent: Intent, contentText: String) {
        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE
            else
                0
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            flag
        )

        notificationBuilder.setContentText(contentText)


        val activeNB = notificationBuilder
            .addAction(
                0,
                "Stop",
                pendingIntent
            )
        val stoppedNB = notificationBuilder
            .addAction(
                0,
                "Start",
                pendingIntent
            )

        val notification = if (active) activeNB.build() else stoppedNB.build()
//        startForeground(1, notification)
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