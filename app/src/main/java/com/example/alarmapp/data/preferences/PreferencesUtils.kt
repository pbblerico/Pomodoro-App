package com.example.alarmapp.data.preferences

import android.content.SharedPreferences

class PreferencesUtils(private var sharedPrefs: SharedPreferences) {
    private val editor = sharedPrefs.edit()
    fun getString(key: Preferences, default: String? = null): String {
        return sharedPrefs.getString(key.name, default).orEmpty()
    }

    fun getInt(key: Preferences, default: Int = 0): Int {
        return sharedPrefs.getInt(key.name, default)
    }

    fun remove(key: Preferences) {
        editor.remove(key.name).apply()
    }

    fun saveString(key: Preferences, value: String) {
        editor
            .putString(key.name, value)
            .apply()
    }

    fun saveInt(key: Preferences, value: Int) {
        editor
            .putInt(key.name, value)
            .apply()
    }
}