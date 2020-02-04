package io.vlinx.java.wrapper.ui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

lateinit var primaryStage: Stage;

class UIApp : Application() {

    override fun start(_primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("ui.fxml"))
        primaryStage = _primaryStage
        _primaryStage.title = APP_TITLE
        _primaryStage.scene = Scene(root)
        _primaryStage.minWidth = 1000.0
        _primaryStage.minHeight = 1000.0
        _primaryStage.show()
    }

    fun Launch(args: Array<String>) {
        launch(*args)
    }
}