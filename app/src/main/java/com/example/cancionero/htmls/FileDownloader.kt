package com.example.cancionero.htmls

import android.app.Activity
import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import okhttp3.OkHttpClient
import okhttp3.Request

object FileDownloader {



    fun downloadHtmlFiles(context: Context, onComplete: (List<File>) -> Unit) {
        Thread {
        val client = OkHttpClient()
        val baseUrl = "https://cancionero-2024.web.app/"  // Cambia por tu URL real
        val fileNames = listOf("canciones1.htm", "canciones2.htm", "canciones3.htm",
            "canciones4.htm", "canciones5.htm", "canciones6.htm",
            "canciones7.htm", "canciones8.htm", "canciones9.htm",
            "canciones10.htm", "canciones11.htm", "canciones12.htm",
            "canciones13.htm", "canciones14.htm", "canciones15.htm",
            "canciones16.htm", "canciones17.htm", "canciones18.htm") // Archivos a descargar

        val storageDir = context.getExternalFilesDir(null)

        // 🧹 1. Eliminar archivos HTML viejos
        storageDir?.listFiles()?.forEach {
            if (it.name.endsWith(".htm")) {
                it.delete()
            }
        }

        val downloadedFiles = mutableListOf<File>()

        // ⬇️ 2. Descargar nuevos archivos
            for (fileName in fileNames) {

                val versionUrl = "$baseUrl${fileName.replace(".htm", ".version")}"
                val htmlUrl = "$baseUrl$fileName"

                val localFile = File(context.getExternalFilesDir(null), fileName)
                val localVersion = File(context.getExternalFilesDir(null), fileName + ".version")

                try{
                    //Descargar versión remota
                    val versionReq = Request.Builder().url(versionUrl).build()
                    val versionResp = client.newCall(versionReq).execute()
                    val remoteVersion = versionResp.body?.string()?.trim()
                    val localVersionText = if (localVersion.exists()) localVersion.readText().trim() else null

                    // Si las versiones son iguales, no se descarga el HTML
                    if (remoteVersion != null && remoteVersion == localVersionText && localFile.exists()) {
                        downloadedFiles.add(localFile)
                        continue
                    }

                    // Descargar archivo HTML
                    val htmlReq = Request.Builder().url(htmlUrl).build()
                    val htmlResp = client.newCall(htmlReq).execute()

                    if (htmlResp.isSuccessful) {
                        FileOutputStream(localFile).use { outputStream ->
                            htmlResp.body?.byteStream()?.copyTo(outputStream)
                        }
                        localVersion.writeText(remoteVersion ?: "1") // Guardar versión descargada
                        downloadedFiles.add(localFile)
                    }

                } catch (e: Exception) {
                    Log.e("Download", "Error con $fileName: ${e.message}")
                }
            }

            (context as Activity).runOnUiThread {
                onComplete(downloadedFiles)
            }

        }.start()



    }
}
