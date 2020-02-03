package io.vlinx.java.wrapper.ui

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import java.net.URL
import java.util.*

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


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        btnStart.graphic = getImageView("icons/start.png")
        btnGuide.graphic = getImageView("icons/guide.png")
        btnAbout.graphic = getImageView("icons/info.png")
        tabBasicInfo.graphic = getImageView("icons/basic.png")
        tabExeInfo.graphic = getImageView("icons/exe.png")
        btnAppFolder.graphic = getImageView("icons/browse.png")
        btnJreFolder.graphic = getImageView("icons/browse.png")
        btnOutputFolder.graphic = getImageView("icons/browse.png")
        btnIcon.graphic = getImageView("icons/browse.png")

        val group = ToggleGroup()
        btnMainClassMode.toggleGroup = group
        btnMainJarMode.toggleGroup = group
        btnMainJarMode.isSelected = true
    }
}