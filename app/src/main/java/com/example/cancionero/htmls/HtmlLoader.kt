package com.example.cancionero.htmls


import android.content.Context
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import java.io.File

object HtmlLoader {

    fun cargarHtml(context: Context, viewPager: ViewPager, onReady: (List<String>) -> Unit) {
        HtmlVersionChecker.fetchVersionFromServer { remoteVersion ->
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val autoUpdate = prefs.getBoolean("auto_update", true)

            if (remoteVersion != null && autoUpdate) {
                FileDownloader.downloadHtmlFiles(context) { files ->
                    if (files.isNotEmpty()) {
                        val htmlFiles = getLocalHtmlFiles(context)
                        onReady(htmlFiles)
                    } else {
                        onReady(getLocalHtmlFiles(context))
                    }
                }
            } else {
                onReady(getLocalHtmlFiles(context))
            }
        }
    }

   private fun getLocalHtmlFiles(context: Context): List<File> {
       val dir = context.getExternalFilesDir(null) ?: return emptyList()
       return dir.listFiles()
            ?.filter { it.name.endsWith(".html") }
            ?.sortedBy {
                // Extrae número del nombre como "canciones1.html" -> 1
                "\\d+".toRegex().find(it.name)?.value?.toIntOrNull() ?: 0}
            ?: emptyList()
}}
