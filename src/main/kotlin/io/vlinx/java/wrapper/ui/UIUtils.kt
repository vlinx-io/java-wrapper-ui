package io.vlinx.java.wrapper.ui

import javafx.scene.control.Alert
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

fun openAlertDialog(title: String, headerText: String?, content: String, type: Alert.AlertType) {
    val alert = Alert(type)
    alert.title = title
    alert.headerText = headerText
    alert.contentText = content
    alert.showAndWait()
}