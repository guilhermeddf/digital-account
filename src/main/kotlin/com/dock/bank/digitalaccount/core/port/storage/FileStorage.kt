package com.dock.bank.digitalaccount.core.port.storage

import java.io.File
import java.io.InputStream

interface FileStorage {
    suspend fun read(fileName: String): String?
    suspend fun save(fileContent: String)
    suspend fun remove(fileName: String)
}