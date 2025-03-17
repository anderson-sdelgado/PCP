package br.com.usinasantafe.pcp.utils

import android.content.Context
import android.util.Log
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FileLoggingTree(private val context: Context) : Timber.Tree() {

    private val logFile = File(context.filesDir, "app_log.txt")
    private val logRetentionPeriod = 30 * 24 * 60 * 60 * 1000L // 30 dias (em milissegundos)

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val logMessage = "$timeStamp - $tag: $message\n${t?.stackTraceToString()}\n"

            try {
                if (!logFile.exists()) {
                    logFile.createNewFile()
                }
                FileWriter(logFile, true).apply {
                    write(logMessage)
                    close()
                }

                cleanOldLogs()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun cleanOldLogs() {
        if (logFile.exists()) {
            val lastModified = logFile.lastModified()
            val currentTime = System.currentTimeMillis()

            if (currentTime - lastModified > logRetentionPeriod) {
                logFile.delete()
            }
        }
    }
}