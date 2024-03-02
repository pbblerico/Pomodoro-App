package com.example.alarmapp.data.repository

import android.content.SharedPreferences
import com.example.alarmapp.data.preferences.Preferences
import com.example.alarmapp.data.preferences.PreferencesUtils
import javax.inject.Inject

interface SharedPrefRepository {
    fun getFocusTime(): Long
    fun getShortBreakTime(): Long
    fun getLongBreakTime(): Long
    fun setFocusTime(millis: Long)
    fun setShortBreak(millis: Long)
    fun setLongBreak(millis: Long)
}

class SharedPrefRepositoryImpl @Inject constructor(
    private val sharedPrefs: PreferencesUtils
): SharedPrefRepository {
    override fun getFocusTime(): Long {
        return sharedPrefs.getLong(Preferences.FOCUS)
    }

    override fun getShortBreakTime(): Long {
        return sharedPrefs.getLong(Preferences.SHORT_BREAK)
    }

    override fun getLongBreakTime(): Long {
        return sharedPrefs.getLong(Preferences.LONG_BREAK)
    }

    override fun setFocusTime(millis: Long) {
        sharedPrefs.saveLong(Preferences.FOCUS, millis)
    }

    override fun setShortBreak(millis: Long) {
        sharedPrefs.saveLong(Preferences.SHORT_BREAK, millis)
    }

    override fun setLongBreak(millis: Long) {
        sharedPrefs.saveLong(Preferences.LONG_BREAK, millis)
    }

}