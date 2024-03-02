package com.example.alarmapp.data.di

import com.example.alarmapp.data.preferences.PreferencesUtils
import com.example.alarmapp.data.repository.SharedPrefRepository
import com.example.alarmapp.data.repository.SharedPrefRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    @Singleton
    fun provideSharedPrefRepository(preferencesUtils: PreferencesUtils): SharedPrefRepository = SharedPrefRepositoryImpl(preferencesUtils)
}