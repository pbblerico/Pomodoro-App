package com.example.alarmapp.data.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarmapp.R
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {
//    @Singleton
//    @Provides
//    fun provideNotificationBuilder(
//        @ApplicationContext context: Context
//    ): NotificationCompat.Builder {
//        return NotificationCompat.Builder(context, "Main Channel ID")
//            .setContentTitle("Pomodoro")
//            .setContentText("YouTube Channel: Stevdza-San")
//            .setSmallIcon(R.drawable.ic_timer)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//    }

//    @Singleton
//    @ServiceScoped
//    @Binds
//    fun provideNotificationManager(
//        @ApplicationContext context: Context
//    ): NotificationManagerCompat {
//        val notificationManager = NotificationManagerCompat.from(context)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                "Main Channel ID",
//                "Main Channel",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            notificationManager.createNotificationChannel(channel)
//        }
//        return notificationManager
//    }
}