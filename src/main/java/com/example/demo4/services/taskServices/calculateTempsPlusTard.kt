package com.example.demo4.services.taskServices

import com.example.demo4.data.AppViewmodel
import javafx.collections.FXCollections
import javafx.collections.ObservableList



fun createTaskFinal(alltasks : ObservableList<TaskDO>, appViewmodel: AppViewmodel) {
    var allTaskAvantFin : ObservableList<TaskDO> = FXCollections.observableArrayList()

    for ( task in alltasks ) {
        if ( task.successeurs.isEmpty() ){
            allTaskAvantFin.add( task )
        }
    }
    appViewmodel.taskDOFin.antecedents = allTaskAvantFin
    for (task in allTaskAvantFin) {
        task.successeurs = FXCollections.observableArrayList(appViewmodel.taskDOFin)
    }
    calculateTempsPlusTot(appViewmodel.taskDOFin)
    appViewmodel.taskList.add(appViewmodel.taskDOFin)
    appViewmodel.taskDOFin.tempsPlusTard = appViewmodel.taskDOFin.tempsPlusTot
//
//    println("Tache FINAL : ")
//    println(" Designation ${appViewmodel.taskDOFin.designation}")
//    println(" Temps plus tôt : ${appViewmodel.taskDOFin.tempsPlusTot}")

}