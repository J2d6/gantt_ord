package com.example.demo4.data

import com.example.demo4.services.taskServices.TaskDO
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class AppViewmodel {
    var taskList : ObservableList<TaskDO> = FXCollections.observableArrayList()
    val nbTaskProperty = SimpleIntegerProperty(this, "nbTask", 0)
    var taskDODebut : TaskDO = TaskDO(0, "DEBUT")
    var taskDOFin : TaskDO = TaskDO(0, "FIN")
    var taskDesigantionList : ObservableList<String> = FXCollections.observableArrayList()

    var nbTask: Int
        get() = nbTaskProperty.get()
        set(value) = nbTaskProperty.set(value)




}