package com.example.cancionero.htmls

import android.content.Context
import android.content.SharedPreferences

object HtmlVersionManager {
    private const val PREF_NAME = "html_version_prefs"
    private const val KEY_VERSION = "html_version_key"

    private fun getLocalVersion(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_VERSION, null)
    }

    fun setLocalVersion(context: Context, version: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_VERSION, version).apply()
    }

    fun isNewVersion(context: Context, remoteVersion: String): Boolean {
        val localVersion = getLocalVersion(context)
        return remoteVersion != localVersion
    }
}

