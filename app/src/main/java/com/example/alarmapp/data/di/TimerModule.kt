package com.example.alarmapp.data.di

import com.example.alarmapp.CustomCountDownTimer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object TimerModule {
    @Provides
    @ServiceScoped
    fun provideTimer() = CustomCountDownTimer()
}