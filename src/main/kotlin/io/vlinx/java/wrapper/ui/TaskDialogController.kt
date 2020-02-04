package io.vlinx.java.wrapper.ui

import io.vlinx.communicate.app.IAppMsgListener
import io.vlinx.java.wrapper.Config
import io.vlinx.java.wrapper.JavaWrapper
import io.vlinx.logging.Logger
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.TextArea
import java.lang.Exception
import java.net.URL
import java.util.*

class TaskDialogController : Initializable {

    @FXML
    lateinit var txtConsole: TextArea

    val listener = IAppMsgListener {
        when (it.command) {
            "debug", "info" -> {
                txtConsole.appendText(it.description + System.lineSeparator())
            }
            "warn" -> {
                txtConsole.appendText(it.description + System.lineSeparator())
            }
            "error", "fatal" -> {
                txtConsole.appendText(it.description + System.lineSeparator())
                openAlertDialog("Error", "null", it.description, Alert.AlertType.ERROR)
            }
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }

    fun startTask(config: Config) {
        val wrapper = JavaWrapper(config)
        wrapper.listener = listener
        val thread = Thread {
            try {
                wrapper.run()
                val log = "Task complete, please check your output folder [${config.outputFolder}]"
                Platform.runLater {
                    openAlertDialog("Complete", null, log, Alert.AlertType.INFORMATION)
                }

            } catch (e: Exception) {
                Logger.ERROR(e)
                Platform.runLater {
                    openAlertDialog("Error", null, e.message!!, Alert.AlertType.ERROR)
                }
            }
        }
        thread.isDaemon = true
        thread.start()
    }


}