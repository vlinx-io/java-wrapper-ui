package io.vlinx.java.wrapper.ui

import io.vlinx.logging.Logger
import java.io.File

lateinit var uiApp: UIApp

fun main(args: Array<String>) {

    Logger.SET_LOG_DIR(resourcesManager.getBasePath() + File.separator + "logs");

    uiApp = UIApp()
    uiApp.Launch(args)

}