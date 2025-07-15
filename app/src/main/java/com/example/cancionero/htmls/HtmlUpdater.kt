package com.example.cancionero.htmls

import android.content.Context
import android.util.Log
import java.io.File
import java.net.URL

object HtmlUpdater {
    private const val VERSION_URL = "https://cancionero-2024.web.app/version.txt"
    private const val HTML_BASE_URL = "https://cancionero-2024.web.app/"
    private val htmlFileNames = listOf(
        "canciones1.htm", "canciones2.htm", "canciones3.htm",
        "canciones4.htm", "canciones5.htm", "canciones6.htm",
        "canciones7.htm", "canciones8.htm", "canciones9.htm",
        "canciones10.htm", "canciones11.htm", "canciones12.htm",
        "canciones13.htm", "canciones14.htm", "canciones15.htm",
        "canciones16.htm", "canciones17.htm", "canciones18.htm"
    )

    fun checkForUpdates(context: Context, onComplete: (Boolean) -> Unit) {
        Thread {
            try {
                val localVersion = getLocalVersion(context)
                val remoteVersion = URL(VERSION_URL).readText().trim()
                val htmlDir = getHtmlDirectory(context)

                val firstTime = localVersion.isBlank()


                if (firstTime || localVersion != remoteVersion) {
                    deleteLocalHtmlFiles(context)

                    htmlFileNames.forEach { name ->
                        val fileUrl = "$HTML_BASE_URL$name"
                        val localFile = File(htmlDir, name)
                        localFile.parentFile?.mkdirs()
                        localFile.writeBytes(URL(fileUrl).readBytes())
                    }

                    saveLocalVersion(context, remoteVersion)
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(false)
            }
        }.start()
    }

    private fun getHtmlDirectory(context: Context): File {
        val dir = File(context.getExternalFilesDir(null), "html")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    private fun deleteLocalHtmlFiles(context: Context) {
        val dir = getHtmlDirectory(context)
        dir.listFiles { _, name -> name.endsWith(".htm") }?.forEach { it.delete() }
    }

    private fun getLocalVersion(context: Context): String {
        val file = File(context.getExternalFilesDir(null), "version.txt")
        return if (file.exists()) file.readText().trim() else ""
    }

    private fun saveLocalVersion(context: Context, version: String) {
        val file = File(context.getExternalFilesDir(null), "version.txt")
        file.writeText(version)
    }

    fun forceDownload(context: Context, onComplete: (Boolean) -> Unit) {
        Thread {
            try {
                val remoteVersion = URL(VERSION_URL).readText().trim()
                val htmlDir = getHtmlDirectory(context)

                deleteLocalHtmlFiles(context)

                htmlFileNames.forEach { name ->
                    val fileUrl = "$HTML_BASE_URL$name"
                    val localFile = File(htmlDir, name)
                    localFile.parentFile?.mkdirs()
                    localFile.writeBytes(URL(fileUrl).readBytes())
                }
                Log.d("HtmlUpdater", "Archivos en disco tras descarga: ${
                    File(context.getExternalFilesDir(null), "html").listFiles()?.joinToString { it.name }
                }")

                saveLocalVersion(context, remoteVersion)
                onComplete(true)
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(false)
            }
        }.start()
    }

}
