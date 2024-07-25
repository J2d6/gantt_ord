package com.example.demo4.services.taskServices

import javafx.collections.FXCollections
import javafx.collections.ObservableList

class TaskDO (
    val duree : Int = 0,
    val designation : String
) {
    var antecedents : ObservableList<TaskDO> = FXCollections.observableArrayList()
    var successeurs : ObservableList<TaskDO> = FXCollections.observableArrayList()
    var tempsPlusTot : Int = 0
    var tempsPlusTard : Int = 0
    var margeLibre : Int = 0
    var margeTotale : Int = 0


    fun allSuccesseurs() : String {
        var allSuccesseursDesignation : String = " "
        for (task in this.successeurs) {
            allSuccesseursDesignation += " ${task.designation}, "

        }
        return allSuccesseursDesignation
    }
}
