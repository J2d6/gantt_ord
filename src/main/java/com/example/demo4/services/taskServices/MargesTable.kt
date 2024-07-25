package com.example.demo4.services.taskServices

import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory

fun createTableView(tasks: ObservableList<TaskDO>): TableView<TaskDO> {
    // Créer la TableView
    val tableView = TableView<TaskDO>()
    val tasksWithoutDebutAndFin = FilteredList(tasks) { it.designation != "DEBUT" && it.designation != "FIN" }
    // Créer les colonnes
    val designationColumn = TableColumn<TaskDO, String>("Désignation").apply {
        cellValueFactory = PropertyValueFactory("designation")
    }
    val margeLibreColumn = TableColumn<TaskDO, Int>("Marge Libre").apply {
        cellValueFactory = PropertyValueFactory("margeLibre")
    }
    val margeTotaleColumn = TableColumn<TaskDO, Int>("Marge Totale").apply {
        cellValueFactory = PropertyValueFactory("margeTotale")
    }

    // Ajouter les colonnes à la TableView
    tableView.columns.addAll(designationColumn, margeLibreColumn, margeTotaleColumn)
    tableView.items = tasksWithoutDebutAndFin // Lier les données

    return tableView
}
