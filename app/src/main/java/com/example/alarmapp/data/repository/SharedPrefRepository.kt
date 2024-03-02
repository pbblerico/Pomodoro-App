package com.example.alarmapp.data.repository

import android.content.SharedPreferences
import com.example.alarmapp.data.model.TimerModel
import com.example.alarmapp.data.preferences.Preferences
import com.example.alarmapp.data.preferences.PreferencesUtils
import com.example.alarmapp.utils.TimerMode
import javax.inject.Inject

interface SharedPrefRepository {
    fun getFocusTime(): Long
    fun getShortBreakTime(): Long
    fun getLongBreakTime(): Long
    fun setFocusTime(millis: Long)
    fun setShortBreak(millis: Long)
    fun setLongBreak(millis: Long)

    fun getCurrMode(): TimerMode
    fun setCurrMode(mode: TimerMode)
    fun getTimeLeft(): Long
    fun setTimeLeft(millis: Long)
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

    override fun getCurrMode(): TimerMode {
        return TimerMode.valueOf(sharedPrefs.getString(Preferences.CURR_MODE, TimerMode.FOCUS.name))
    }

    override fun setCurrMode(mode: TimerMode) {
        sharedPrefs.saveString(Preferences.CURR_MODE, mode.name)
    }

    override fun getTimeLeft(): Long {
        return sharedPrefs.getLong(Preferences.TIME_LEFT)
    }

    override fun setTimeLeft(millis: Long) {
        sharedPrefs.saveLong(Preferences.TIME_LEFT, millis)
    }


}