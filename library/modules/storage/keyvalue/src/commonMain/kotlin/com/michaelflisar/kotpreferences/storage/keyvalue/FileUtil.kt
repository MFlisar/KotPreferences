package com.michaelflisar.kotpreferences.storage.keyvalue

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.IOException
import okio.Path
import okio.SYSTEM
import okio.buffer
import okio.use

object FileUtil {

    fun exists(filePath: Path) = FileSystem.SYSTEM.exists(filePath)

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
        FileSystem.SYSTEM.sink(filePath).use { fileSink ->
            fileSink.buffer().use { bufferedSink ->
                for (d in data) {
                    bufferedSink.writeUtf8(d.key)
                    bufferedSink.writeUtf8(delimiter)
                    bufferedSink.writeUtf8(d.value)
                    bufferedSink.writeUtf8("\n")
                }
            }
        }
    }

    @Throws(IOException::class)
    fun readLines(
        filePath: Path
    ): List<String> {
        val lines = ArrayList<String>()
        if (FileSystem.SYSTEM.exists(filePath)) {
            FileSystem.SYSTEM.read(filePath) {
                while (true) {
                    val line = readUtf8Line() ?: break
                    lines += line
                }
            }
        }
        return lines
    }
}