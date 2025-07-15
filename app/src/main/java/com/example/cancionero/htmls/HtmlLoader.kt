package com.example.cancionero.htmls


import android.content.Context
import android.util.Log
import java.io.File
object HtmlLoader {

    /**
     * Devuelve la lista de rutas de archivos HTML locales.
     * Si hay archivos en almacenamiento externo, los usa.
     * Si no, devuelve las rutas a los archivos embebidos en assets/html/.
     */
    fun getLocalHtmlFiles(context: Context): List<String> {
        val externalDir = File(context.getExternalFilesDir(null), "html")
        if (!externalDir.exists()) externalDir.mkdirs()

        val externalFiles = externalDir.listFiles { _, name -> name.endsWith(".htm") }
            ?.sortedBy {  // Extraer el número del nombre del archivo (por ejemplo: canciones12.htm → 12)
                Regex("""\d+""").find(it.name)?.value?.toIntOrNull() ?: 0 }
            ?.map { "file://${it.absolutePath}"} ?: emptyList()

        return if (externalFiles.isNotEmpty()) {
            externalFiles
        } else {
            getAssetHtmlPaths(context)
        }

    }

    /**
     * Copia los archivos HTML embebidos desde assets/html/ a la carpeta externa,
     * solo si todavía no hay archivos .htm en esa carpeta.
     */
    fun copyDefaultHtmlFilesIfNeeded(context: Context) {
        val htmlDir = File(context.getExternalFilesDir(null), "html")
        if (htmlDir.exists()) {
            val files = htmlDir.listFiles { _, name -> name.endsWith(".htm") }
            if (files != null && files.isNotEmpty()) {
                // Ya hay archivos, no copiar
                return
            }
        } else {
            htmlDir.mkdirs()
        }

        val assetManager = context.assets
        try {
            val assetFiles = assetManager.list("html") ?: emptyArray()
            for (fileName in assetFiles) {
                if (fileName.endsWith(".htm")) {
                    val inputStream = assetManager.open("html/$fileName")
                    val outFile = File(htmlDir, fileName)
                    inputStream.use { input ->
                        outFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Devuelve rutas para cargar archivos embebidos en assets/html con WebView.
     * Formato: "file:///android_asset/html/archivo.htm"
     */
    private fun getAssetHtmlPaths(context: Context): List<String> {
        return try {
            val assetFiles = context.assets.list("html")
                ?.filter { it.endsWith(".htm") }
                ?.sorted() ?: emptyList()

            assetFiles.map { "file:///android_asset/html/$it" }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
