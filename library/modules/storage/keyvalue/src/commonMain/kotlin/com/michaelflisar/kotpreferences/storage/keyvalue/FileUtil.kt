package com.michaelflisar.kotpreferences.storage.keyvalue

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readLine
import kotlinx.io.writeString

object FileUtil {

    fun exists(filePath: Path) = SystemFileSystem.exists(filePath)

    fun read(
        filePath: Path,
        delimiter: String
    ): MutableMap<String, String> {
        return readLines(filePath).associate {
            val parts = it.split(delimiter)
            val key = parts[0]
            val data = parts.drop(1).joinToString(delimiter).takeIf { it.isNotEmpty() } ?: ""
            Pair(key, data)
        }.toMutableMap()
    }

    suspend fun save(
        data: Map<String, String>,
        filePath: Path,
        delimiter: String
    ) {
        withContext(Dispatchers.IO) {
            writeLines(filePath, data, delimiter)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun saveAsync(
        data: Map<String, String>,
        filePath: Path,
        delimiter: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            save(data, filePath, delimiter)
        }
    }

    @kotlin.Throws(IOException::class)
    private fun writeLines(
        filePath: Path,
        data: Map<String, String>,
        delimiter: String
    ) {
        SystemFileSystem.sink(filePath).buffered().use { fileSink ->
            fileSink.use { bufferedSink ->
                for (d in data) {
                    bufferedSink.writeString(d.key)
                    bufferedSink.writeString(delimiter)
                    bufferedSink.writeString(d.value)
                    bufferedSink.writeString("\n")
                }
            }
        }
    }

    @Throws(IOException::class)
    fun readLines(
        filePath: Path
    ): List<String> {
        val lines = ArrayList<String>()
        if (SystemFileSystem.exists(filePath)) {
            SystemFileSystem.source(filePath).buffered().use { bufferedSource ->
                while (true) {
                    val line = bufferedSource.readLine() ?: break
                    lines += line
                }
            }
        }
        return lines
    }
}