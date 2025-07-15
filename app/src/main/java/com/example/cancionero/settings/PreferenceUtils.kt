package com.example.cancionero.settings

import android.content.Context
import androidx.preference.PreferenceManager

object PreferenceUtils {
    fun loadChordVisibilityPreference(context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean("show_chords", true)
    }
}
