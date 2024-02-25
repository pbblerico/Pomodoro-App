package com.example.alarmapp.data.preferences

import android.content.SharedPreferences

class PreferencesUtils(private var sharedPrefs: SharedPreferences) {
    val editor = sharedPrefs.edit()
    fun getString(key: Preferences, default: String? = null): String {
        return sharedPrefs.getString(key.name, default).orEmpty()
    }

    fun getLong(key: Preferences, default: Long = 0): Long {
        return sharedPrefs.getLong(key.name, default)
    }

    fun remove(key: Preferences) {
        sharedPrefs.edit().remove(key.name).apply()
    }

    fun saveString(key: Preferences, value: String) {
        editor
            .putString(key.name, value)
            .apply()
    }

    fun saveLong(key: Preferences, value: Long) {
        editor
            .putLong(key.name, value)
            .apply()
    }
}