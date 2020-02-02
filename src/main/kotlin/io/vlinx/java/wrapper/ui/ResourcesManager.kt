package io.vlinx.java.wrapper.ui

import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLDecoder


val resourcesManager = ResourcesManager()

class ResourcesManager {
    fun getResourcePath(relativePath: String): String? {
        return try {
            URLDecoder.decode(getBasePath() + File.separator + relativePath, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            getBasePath() + File.separator + relativePath
        }
    }

    fun getBasePath(): String {
        val file =
            File(ResourcesManager::class.java.getProtectionDomain().getCodeSource().getLocation().getPath())
        var absPath = ""
        absPath = if (file.isDirectory) {
            file.absolutePath
        } else {
            file.parentFile.absolutePath
        }
        return try {
            URLDecoder.decode(absPath, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            absPath
        }
    }
}