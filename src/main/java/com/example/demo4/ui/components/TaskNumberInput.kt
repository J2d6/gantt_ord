package com.example.demo4.ui.components

import com.example.demo4.data.AppViewmodel
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

typealias NavigationButtonAction =  (Any?) -> Unit

class TaskNumberInput (
    val appViewmodel: AppViewmodel,
    val navigationButtonAction : NavigationButtonAction
): VBox() {

    var ordButtonOnAction : NavigationButtonAction = {}

    init {
        createContent()
    }

    private fun createContent() {
        alignment = Pos.CENTER
        children += VBox().apply {
            maxWidth = 650.0
            spacing = 10.0
            children += Label("Nombre de tâches à ordonner : ")
            var textField = TextField().apply {
                promptText = "Nombre de tâches"
            }
            children += textField
            children += HBox().apply {
                alignment = Pos.CENTER_RIGHT
                children += Button("Ordonnancer").apply {
                    onAction = EventHandler<ActionEvent> {
                        appViewmodel.nbTask = textField.text.toInt()
                        navigationButtonAction(null)
                    }
                }
            }
        }
    }


}