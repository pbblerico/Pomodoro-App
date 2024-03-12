package com.example.alarmapp.data.repository

import com.example.alarmapp.data.preferences.Preferences
import com.example.alarmapp.data.preferences.PreferencesUtils
import javax.inject.Inject

interface SharedPrefRepository {
    fun getFocusTime(): Int
    fun getShortBreakTime(): Int
    fun getLongBreakTime(): Int
    fun setFocusTime(seconds: Int)
    fun setShortBreak(seconds: Int)
    fun setLongBreak(seconds: Int)
}

class SharedPrefRepositoryImpl @Inject constructor(
    private val sharedPrefs: PreferencesUtils
): SharedPrefRepository {
    override fun getFocusTime(): Int {
        return sharedPrefs.getInt(Preferences.FOCUS, 0)
    }

    override fun getShortBreakTime(): Int {
        return sharedPrefs.getInt(Preferences.SHORT_BREAK, 0)
    }

    override fun getLongBreakTime(): Int {
        return sharedPrefs.getInt(Preferences.LONG_BREAK, 0)
    }

    override fun setFocusTime(seconds: Int) {
        sharedPrefs.saveInt(Preferences.FOCUS, seconds)
    }

    override fun setShortBreak(seconds: Int) {
        sharedPrefs.saveInt(Preferences.SHORT_BREAK, seconds)
    }

    override fun setLongBreak(seconds: Int) {
        sharedPrefs.saveInt(Preferences.LONG_BREAK, seconds)
    }
}