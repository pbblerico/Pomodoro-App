package com.example.alarmapp.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmapp.data.preferences.PreferencesUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("APP", AppCompatActivity.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferencesUtils(preferences: SharedPreferences): PreferencesUtils {
        return PreferencesUtils(preferences)
    }
}