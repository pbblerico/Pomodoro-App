package com.example.alarmapp.data.repository

import com.example.alarmapp.data.preferences.Preferences
import com.example.alarmapp.data.preferences.PreferencesUtils
import com.example.alarmapp.utils.TimerMode
import javax.inject.Inject

interface SharedPrefRepository {
    fun getFocusTime(): Int
    fun getShortBreakTime(): Int
    fun getLongBreakTime(): Int
    fun setFocusTime(seconds: Int)
    fun setShortBreak(seconds: Int)
    fun setLongBreak(seconds: Int)

    fun getCurrMode(): TimerMode
    fun setCurrMode(mode: TimerMode)
}

class SharedPrefRepositoryImpl @Inject constructor(
    private val sharedPrefs: PreferencesUtils
): SharedPrefRepository {
    override fun getFocusTime(): Int {
        return sharedPrefs.getInt(Preferences.FOCUS)
    }

    override fun getShortBreakTime(): Int {
        return sharedPrefs.getInt(Preferences.SHORT_BREAK)
    }

    override fun getLongBreakTime(): Int {
        return sharedPrefs.getInt(Preferences.LONG_BREAK)
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

    override fun getCurrMode(): TimerMode {
        return TimerMode.valueOf(sharedPrefs.getString(Preferences.CURR_MODE, TimerMode.FOCUS.name))
    }

    override fun setCurrMode(mode: TimerMode) {
        sharedPrefs.saveString(Preferences.CURR_MODE, mode.name)
    }

}