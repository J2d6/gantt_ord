package com.example.demo4.services.taskServices

import javafx.collections.FXCollections
import javafx.collections.ObservableList

fun calculateTempsPlusTot(taskDO: TaskDO) {
    var tempsPlutotAntecedent: ObservableList<Int> = FXCollections.observableArrayList()

    for (antecedent in taskDO.antecedents) {
        tempsPlutotAntecedent.add(antecedent.tempsPlusTot+antecedent.duree)
    }
    taskDO.tempsPlusTot = tempsPlutotAntecedent.maxOrNull() ?: 0

    for (successeur in taskDO.successeurs) {
        calculateTempsPlusTot(successeur)
    }

}
fun calculateTempsPlusTard(taskDO: TaskDO) {


    if(!taskDO.antecedents.isEmpty()) {
        if(taskDO.successeurs.isEmpty()) {
            // FIN
            taskDO.tempsPlusTard = taskDO.tempsPlusTot
        } else {
            // alai
        }
    }
    var tempsPluTardSuccesseur: ObservableList<Int> = FXCollections.observableArrayList()

    if (taskDO.successeurs.isEmpty()) {
        taskDO.tempsPlusTard = taskDO.tempsPlusTot
    } else {
        for (successeur in taskDO.successeurs) {
            tempsPluTardSuccesseur.add(successeur.tempsPlusTard)
        }
        var minTempsPlusTotSuccesseurs : Int =  tempsPluTardSuccesseur.min()

        taskDO.tempsPlusTard = minTempsPlusTotSuccesseurs-taskDO.duree
    }

    for (ant in taskDO.antecedents) {
        calculateTempsPlusTard(ant)
    }
}