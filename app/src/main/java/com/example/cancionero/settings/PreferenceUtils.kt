package com.example.cancionero.settings

import android.content.Context

object PreferenceUtils {

    fun saveChordVisibilityPreference(context: Context, visible: Boolean) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("show_chords", visible).apply()
    }

    fun loadChordVisibilityPreference(context: Context): Boolean {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("show_chords", true)
    }
}
