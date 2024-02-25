package com.example.alarmapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alarmapp.TimerViewModel
import com.example.alarmapp.TimerViewModel_Factory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
object ViewModelModule {
    fun provideTimerViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): TimerViewModel {
        return ViewModelProvider(fragment, factory)[TimerViewModel::class.java]
    }
}