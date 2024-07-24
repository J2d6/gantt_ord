package com.example.demo4.utilities

import com.example.demo4.services.taskServices.TaskDO
import javafx.collections.FXCollections
import javafx.collections.ObservableList


fun generateUppercaseLetters(n: Int): ObservableList<String> {
    require(n in 1..26) { "n doit être entre 1 et 26" }
    val letters = ('A'..'Z').take(n).map { it.toString() }
    return FXCollections.observableArrayList(letters)
}


fun generateAntecedantTasks(n : Int, designationList : ObservableList<String>) : ObservableList<TaskDO>{
    var anterieurGenereated : ObservableList<TaskDO> = FXCollections.observableArrayList()
    for (designation in designationList) {
        var taskDO : TaskDO = TaskDO(0, designation)
        anterieurGenereated.add(taskDO)
    }
    return anterieurGenereated

}