package com.example.cancionero.htmls

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

object HtmlVersionChecker {
    fun fetchVersionFromServer(onResult: (String?) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://cancionero-2024.web.app/version.json") // Coloca aquí tu URL
            .build()

        Thread {
            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val version = response.body?.string()?.trim()
                        onResult(version)
                    } else {
                        Log.e("VersionCheck", "Falló la descarga: ${response.code}")
                        onResult(null)
                    }
                }
            } catch (e: Exception) {
                Log.e("VersionCheck", "Error al obtener versión", e)
                onResult(null)
            }
        }.start()
    }
}
