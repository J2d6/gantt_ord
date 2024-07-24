package com.example.demo4.ui.components

import com.example.demo4.data.AppViewmodel
import com.example.demo4.services.taskServices.TaskDO
import com.example.demo4.services.taskServices.calculateTempsPlusTard
import com.example.demo4.services.taskServices.calculateTempsPlusTot
import com.example.demo4.services.taskServices.createTaskFinal
import com.example.demo4.utilities.generateAntecedantTasks
import com.example.demo4.utilities.generateUppercaseLetters
import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*

typealias ButtonRunner = () -> Unit

class TaskDetailInput(
    val appViewmodel: AppViewmodel,
    val navigationButtonAction: NavigationButtonAction
) : VBox() {


    private val nbTaskMaxProperty: IntegerProperty = appViewmodel.nbTaskProperty
    var dureeProperty: StringProperty = SimpleStringProperty()
    var designationProperty: StringProperty = SimpleStringProperty()
    var antecedentList: ObservableList<TaskDO> = FXCollections.observableArrayList(
        appViewmodel.taskDODebut
    )
    var antecedentTaskSelected : ObservableList<TaskDO> = FXCollections.observableArrayList()
    var disableInputProperty : BooleanProperty = SimpleBooleanProperty(true)

    init {
        createContent()
        disableInputProperty.bind(nbTaskMaxProperty.lessThanOrEqualTo(0))
        antecedentList.addAll( generateAntecedantTasks(
            nbTaskMaxProperty.get(),
            generateUppercaseLetters(nbTaskMaxProperty.get())
        ))
        appViewmodel.taskDesigantionList = FXCollections.observableArrayList(
            generateUppercaseLetters(nbTaskMaxProperty.get())
        )
    }

    private fun createContent() {
        alignment = Pos.CENTER
        spacing = 10.0
        children += createRowInput("Désignation : ", "tâche", designationProperty)
        children += createRowInput("Durée de la tâche : ", "durée", dureeProperty)
        children += HBox().apply {
            padding = Insets(0.0, 0.0, 0.0, 75.0)
            spacing = 5.0
            children += Label("Tâches antérieures : ")
            children += Button("Tâches antérieures").apply {
                onAction = EventHandler {
                    showAntecedentListDialog()
                }
            }
        }
        children += HBox().apply {
            spacing = 10.0
            prefWidth = 650.0
            maxWidth = 650.0
            alignment = Pos.CENTER_RIGHT

            children += Button("Ordonnancer").apply {
                visibleProperty().bind(disableInputProperty)
                onAction = EventHandler {
                    ordonnancer()
                }
            }

            children += Button("Ajouter").apply {
                disableProperty().bind(disableInputProperty)

                onAction = EventHandler {
                    if (nbTaskMaxProperty.get() > 0) {
                        nbTaskMaxProperty.set(nbTaskMaxProperty.get() - 1)

                        var task : TaskDO = TaskDO (
                            dureeProperty.value.toInt(),
                            designationProperty.value
                        )
                        addTask(task)

                        dureeProperty.set("")
                        designationProperty.set("")
                    }
                    println(appViewmodel.nbTask)
                }
            }
        }
    }

    private fun createRowInput(label: String, promptText: String, stringProperty : StringProperty ) = VBox().apply {
        prefWidth = 650.0
        maxWidth = 650.0
        spacing = 5.0
        children += Label(label)
        var textField = TextField().apply {
            this.promptText = promptText
        }
        textField.textProperty().bindBidirectional(stringProperty)
        textField.disableProperty().bind(disableInputProperty)
        children += textField
    }

    fun addTask(taskDO: TaskDO) {

        val index = antecedentList.indexOfFirst { it.designation == taskDO.designation }
        if (index != -1) {
            antecedentList[index] = taskDO
        } else {
            println("Task with id ${taskDO.designation} not found.")
        }
       // antecedentList.add(taskDO)
        // ajout antécedents
        taskDO.antecedents = antecedentTaskSelected
        for (antecedent in taskDO.antecedents) {
            antecedent.successeurs.add(taskDO)
        }
        //

    }

    fun showAntecedentListDialog() {
        val dialog = Dialog<String>()
        dialog.title = "Tâches antécédentes"
        dialog.headerText = "Selectionner les tâches antécédentes"

        // Create the ListView and add items
        val listView = ListView<TaskDO>()
        // Set a cell factory to display the name of each task
        listView.setCellFactory { listCell ->
            object : ListCell<TaskDO>() {
                override fun updateItem(item: TaskDO?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (empty || item == null) "" else item.designation
                }
            }
        }
        listView.items = antecedentList
        listView.selectionModel.selectionMode = SelectionMode.MULTIPLE

        antecedentTaskSelected = listView.selectionModel.selectedItems
        // Add the ListView to the dialog's dialogPane
        dialog.dialogPane.content = listView

        // Add OK and Cancel buttons
        dialog.dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL)

        dialog.showAndWait()

    }

    fun ordonnancer() {
        calculateTempsPlusTot(appViewmodel.taskDODebut)
        createTaskFinal(antecedentList, appViewmodel)
        calculateTempsPlusTard(appViewmodel.taskFnal)
        appViewmodel.taskList = antecedentList
        for (task in antecedentList) {
            println("Tache : ${task.designation}")
            println("Duree : ${task.duree}")
            println("Temps plus tot : ${task.tempsPlusTot}")
            println("Temps plus tard : ${task.tempsPlusTard}")
        }
        navigationButtonAction(null)
    }

}
