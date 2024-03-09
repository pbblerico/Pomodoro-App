package com.example.alarmapp

import android.app.Application
import android.content.SharedPreferences
import dagger.Component
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}