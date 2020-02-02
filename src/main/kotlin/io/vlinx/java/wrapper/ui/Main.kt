package io.vlinx.java.wrapper.ui

import io.vlinx.logging.Logger
import java.io.File

fun main(args: Array<String>) {

    Logger.SET_LOG_DIR(resourcesManager.getBasePath() + File.separator + "logs");

    val app = UIApp()
    app.Launch(args)

}