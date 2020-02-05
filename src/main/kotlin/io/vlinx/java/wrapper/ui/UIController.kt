package io.vlinx.java.wrapper.ui

import io.vlinx.java.wrapper.Config
import io.vlinx.utils.SystemInfo
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import javafx.stage.Modality
import javafx.stage.Stage
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class UIController : Initializable {

    @FXML
    lateinit var btnStart: Button
    @FXML
    lateinit var btnGuide: Button
    @FXML
    lateinit var btnAbout: Button
    @FXML
    lateinit var tabBasicInfo: Tab
    @FXML
    lateinit var tabExeInfo: Tab
    @FXML
    lateinit var btnAppFolder: Button
    @FXML
    lateinit var btnJreFolder: Button
    @FXML
    lateinit var btnOutputFolder: Button
    @FXML
    lateinit var btnMainJarMode: RadioButton
    @FXML
    lateinit var btnMainClassMode: RadioButton
    @FXML
    lateinit var ckHideConsole: CheckBox
    @FXML
    lateinit var btnIcon: Button
    @FXML
    lateinit var txtAppFolder: TextField
    @FXML
    lateinit var txtJreFolder: TextField
    @FXML
    lateinit var txtMainJar: TextField
    @FXML
    lateinit var txtMainClass: TextField
    @FXML
    lateinit var txtClasspath: TextArea
    @FXML
    lateinit var txtExeName: TextField
    @FXML
    lateinit var txtOutputFolder: TextField
    @FXML
    lateinit var txtIcon: TextField
    @FXML
    lateinit var txtProductName: TextField
    @FXML
    lateinit var txtFileVersion: TextField
    @FXML
    lateinit var txtFileDescription: TextField
    @FXML
    lateinit var txtProductVersion: TextField
    @FXML
    lateinit var txtCompanyName: TextField
    @FXML
    lateinit var txtCopyright: TextField
    @FXML
    lateinit var cmbTargetPlatform: ComboBox<String>
    @FXML
    lateinit var txtJvmOptions: TextArea

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val group = ToggleGroup()
        btnMainClassMode.toggleGroup = group
        btnMainJarMode.toggleGroup = group
        btnMainJarMode.isSelected = true

        cmbTargetPlatform.items.addAll("win64", "win32", "linux64", "linux32", "mac")
        cmbTargetPlatform.value = SystemInfo.getPlatform()

        if (!SystemInfo.getPlatform().startsWith("win")) {
            tabExeInfo.isDisable = true
            tabExeInfo.tooltip = Tooltip("only valid under Windows")
        }

        initIcons()
    }

    private fun initIcons() {
        btnStart.graphic = getImageView("icons/start.png")
        btnGuide.graphic = getImageView("icons/guide.png")
        btnAbout.graphic = getImageView("icons/info.png")
        tabBasicInfo.graphic = getImageView("icons/basic.png")
        tabExeInfo.graphic = getImageView("icons/exe.png")
        btnAppFolder.graphic = getImageView("icons/browse.png")
        btnJreFolder.graphic = getImageView("icons/browse.png")
        btnOutputFolder.graphic = getImageView("icons/browse.png")
        btnIcon.graphic = getImageView("icons/browse.png")
    }


    @FXML
    fun handleStart(actionEvent: ActionEvent) {

        val config = generateConfig()
        val valid = checkValid(config)

        if (!valid) {
            return
        }

        openTaskDialog(config)
    }

    private fun generateConfig(): Config {
        val config = Config()
        config.appFolder = txtAppFolder.text
        config.jreFolder = txtJreFolder.text
        if (btnMainJarMode.isSelected) {
            config.mode = MAIN_JAR_MODE
        } else if (btnMainClassMode.isSelected) {
            config.mode = MAIN_CLASS_MODE
        }
        config.mainJar = txtMainJar.text
        config.mainClass = txtMainClass.text
        config.classpath = txtClasspath.text.split(":", ";")
        config.targetPlatform = cmbTargetPlatform.value
        config.exeFileName = txtExeName.text
        config.outputFolder = txtOutputFolder.text
        config.isHideConsole = ckHideConsole.isSelected

        val jvmOptions = txtJvmOptions.text.split(" ", "\n")
        val jvmOptionsList = ArrayList<String>()

        for (option in jvmOptions) {
            if (option.trim().isNotEmpty()) {
                jvmOptionsList.add(option)
            }
        }
        config.jvmOptions = jvmOptionsList

        config.icon = txtIcon.text
        config.productName = txtProductName.text
        config.fileVersion = txtFileVersion.text
        config.fileDescription = txtFileDescription.text
        config.productVersion = txtProductVersion.text
        config.companyName = txtCompanyName.text
        config.legalCopyright = txtCopyright.text

        return config
    }

    fun checkValid(config: Config): Boolean {

        if (config.appFolder.isNullOrEmpty()) {
            openAlertDialog("Error", null, "App folder should not be empty", Alert.AlertType.ERROR)
            return false
        }
        if (config.mode == MAIN_JAR_MODE && config.mainJar.isNullOrEmpty()) {
            openAlertDialog("Error", null, "Please specify a main jar file", Alert.AlertType.ERROR)
            return false
        }
        if (config.mode == MAIN_CLASS_MODE && config.mainClass.isNullOrEmpty()) {
            openAlertDialog("Error", null, "Please specify a main class", Alert.AlertType.ERROR)
            return false
        }

        if (config.mode == MAIN_CLASS_MODE && config.classpath.isEmpty()) {
            openAlertDialog("Error", null, "There is no classpath specified", Alert.AlertType.ERROR)
            return false
        }

        if (config.exeFileName.isNullOrEmpty()) {
            openAlertDialog("Error", null, "Please specify the executable file name", Alert.AlertType.ERROR)
            return false
        }

        if (config.outputFolder.isNullOrEmpty()) {
            openAlertDialog("Error", null, "Output folder should not be empty", Alert.AlertType.ERROR)
            return false
        }

        return true
    }

    private fun openTaskDialog(config: Config) {
        val loader = FXMLLoader()
        val layout = loader.load<Parent>(javaClass.classLoader.getResourceAsStream("task.fxml"))
        val taskScene = Scene(layout)
        val taskStage = Stage()
        taskStage.minWidth = 800.0
        taskStage.minHeight = 600.0
        taskStage.title = "Task Console"

        val controller = loader.getController<TaskDialogController>()

        taskStage.initModality(Modality.WINDOW_MODAL)
        taskStage.initOwner(primaryStage)

        taskStage.scene = taskScene
        taskStage.show()
        controller.startTask(config)
    }

    @FXML
    fun handleSelectAppFolder(actionEvent: ActionEvent) {
        val chooser = DirectoryChooser()
        val selectedDir = chooser.showDialog(primaryStage)
        if (selectedDir != null) {
            txtAppFolder.text = selectedDir.absolutePath
        }
    }

    fun handleSelectJreFolder(actionEvent: ActionEvent) {
        val chooser = DirectoryChooser()
        val selected = chooser.showDialog(primaryStage)
        if (selected != null) {
            txtJreFolder.text = selected.absolutePath
        }
    }

    fun handleSelectOutputFolder(actionEvent: ActionEvent) {
        val chooser = DirectoryChooser()
        val selected = chooser.showDialog(primaryStage)
        if (selected != null) {
            txtOutputFolder.text = selected.absolutePath
        }
    }

    fun handleSelectIcon(actionEvent: ActionEvent) {
        var chooser = FileChooser()
        chooser.extensionFilters.addAll(
            FileChooser.ExtensionFilter("Icon files", "*.ico"),
            FileChooser.ExtensionFilter("All files", "*.*")
        )

        val selected = chooser.showOpenDialog(primaryStage)
        if (selected != null) {
            txtIcon.text = selected.absolutePath
        }
    }

    fun selectMainJarMode(actionEvent: ActionEvent) {
        if (btnMainJarMode.isSelected) {
            txtMainJar.isDisable = false
            txtMainClass.isDisable = true
            txtClasspath.isDisable = true
        }
    }

    fun selectMainClassMode(actionEvent: ActionEvent) {
        if (btnMainClassMode.isSelected) {
            txtMainClass.isDisable = false
            txtClasspath.isDisable = false
            txtMainJar.isDisable = true
        }
    }

    fun handleGuide(actionEvent: ActionEvent) {


    }

    fun handleAbout(actionEvent: ActionEvent) {

    }
}