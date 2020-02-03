package io.vlinx.java.wrapper.ui

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.lang.Exception

fun getImageView(path: String): ImageView? {
    try {
        val img = Image(object {}.javaClass.classLoader.getResourceAsStream(path))
        return ImageView(img)
    } catch (e: Exception) {
        return null
    }
}