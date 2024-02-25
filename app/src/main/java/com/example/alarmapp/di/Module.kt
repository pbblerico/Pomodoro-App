package com.example.alarmapp.di

import com.example.alarmapp.SharedPrefRepository
import com.example.alarmapp.SharedPrefRepositoryImpl
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
    fun provideSharedPrefRepository(): SharedPrefRepository = SharedPrefRepositoryImpl()
}