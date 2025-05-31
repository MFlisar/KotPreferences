package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.StorageContext
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readLine
import kotlinx.io.readString
import kotlinx.io.writeString

object FileUtil {

    fun exists(filePath: Path) = SystemFileSystem.exists(filePath)

    @Throws(IOException::class)
    fun readFile(
        filePath: Path,
    ): String {
        if (SystemFileSystem.exists(filePath)) {
            SystemFileSystem.source(filePath).buffered().use { bufferedSource ->
                return bufferedSource.readString()
            }
        } else {
            return ""
        }
    }

    suspend fun writeFile(
        filePath: Path,
        content: String,
    ) {
        @OptIn(InternalApi::class)
        withContext(StorageContext) {
            SystemFileSystem.sink(filePath).buffered().use { fileSink ->
                fileSink.use { bufferedSink ->
                    bufferedSink.writeString(content)
                }
            }
        }
    }
}