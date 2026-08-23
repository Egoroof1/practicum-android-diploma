package ru.practicum.android.diploma.data.storage

import android.content.SharedPreferences

class SharedPreferencesStorage(
    val sharedPreferences: SharedPreferences
) {

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    fun removeFilter(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun setString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? = sharedPreferences.getString(key, null)

    fun setInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int? {
        if (!sharedPreferences.contains(key)) return null
        return sharedPreferences.getInt(key, 0)
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean? {
        if (!sharedPreferences.contains(key)) return null
        return sharedPreferences.getBoolean(key, false)
    }
}
