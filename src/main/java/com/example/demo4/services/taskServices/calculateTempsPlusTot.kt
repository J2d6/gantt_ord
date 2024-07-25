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
fun calculateTempsPlusTard(taskDO : TaskDO) {
    val tempsPlusTardSucceurs : ObservableList<Int> = FXCollections.observableArrayList()

    for (succ in taskDO.successeurs) {
        tempsPlusTardSucceurs.add(succ.tempsPlusTard- taskDO.duree)
    }
    taskDO.tempsPlusTard = tempsPlusTardSucceurs.min()
    for (ant in taskDO.antecedents) {
        calculateTempsPlusTard(ant)
    }

//    for (task in taskDOList) {
//        if(task.successeurs.isEmpty()) {
//            // task == FIN
//            task.tempsPlusTard = task.tempsPlusTot
//        } else {
//            var tempsPluTardListSuccesseur: ObservableList<Int> = FXCollections.observableArrayList()
//            for (succ in task.successeurs) {
//                tempsPluTardListSuccesseur.add(succ.tempsPlusTard)
//            }
//            task.tempsPlusTard = tempsPluTardListSuccesseur.min() - task.duree
//        }
//    }

}