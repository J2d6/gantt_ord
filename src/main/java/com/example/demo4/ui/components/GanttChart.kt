package com.example.demo4.ui.components

import com.example.demo4.data.AppViewmodel
import com.example.demo4.services.taskServices.TaskDO
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Series
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Screen

class GanttChart(
    val appViewmodel: AppViewmodel,
    //val navigationButtonAction: NavigationButtonAction = {_ -> {}}
)  : VBox() {

    init {
        createContent()
    }

    private fun createContent() {
        children += VBox().apply {
            val lineChart = initGanttChart()
            spacing = 5.0
            setVgrow(lineChart, Priority.ALWAYS)
            children += lineChart
            children += cheminCritique()
        }
    }

    fun initGanttChart() : LineChart<Number, Number> {
        minHeight = Screen.getPrimary().visualBounds.height/(3/4)
        // create axises
        var dateAxis = NumberAxis()
        dateAxis.label = "Date"
        var taskAxis = NumberAxis()
        taskAxis.label = "Tâches"
        //taskAxis.categories = appViewmodel.taskDesigantionList

        var ganttChart : LineChart<Number, Number> = LineChart(dateAxis, taskAxis)
        ganttChart.title = "Diagramme de GANTT"


        val seriesListTot : ObservableList<Series<Number, Number>> = FXCollections.observableArrayList()
        var seriesPluTot = createSerieTotListFromManagedTasks(appViewmodel.taskDODebut, seriesListTot)

        val seriesListTard : ObservableList<Series<Number, Number>> = FXCollections.observableArrayList()
        var seriesPluTard = createSerieTardListFromManagedTasks(appViewmodel.taskDODebut, seriesListTard)

        ganttChart.data.addAll(seriesPluTot )
        ganttChart.data.addAll(seriesPluTard)

        // Appliquer les styles après que le LineChart soit affiché
        Platform.runLater {
            applyStyles(seriesListTot, "-fx-stroke: blue;")
            applyStyles(seriesListTard, "-fx-stroke: red;")
        }

        return ganttChart

    }

    private fun applyStyles(seriesGroup: ObservableList<Series<Number, Number>>, style: String) {
        for (serie in seriesGroup) {
            serie.node.style = style
        }
    }


    /**
     * Transforme un TaskDO en une serie
     */
    fun createTaskSeriePluTot(taskDO: TaskDO) : Series<Number,Number >{

        var seriePlusTot : Series<Number, Number> = Series()
        val index = appViewmodel.taskDesigantionList.indexOfFirst {
            it == taskDO.designation
        }
        val indexInGantt = appViewmodel.taskDesigantionList.count()-index
        println("index de ${taskDO.designation} : $index ")
        seriePlusTot.data.add(XYChart.Data(taskDO.tempsPlusTot, indexInGantt))
        seriePlusTot.data.add(XYChart.Data(taskDO.tempsPlusTot + taskDO.duree,indexInGantt))
        seriePlusTot.name = "${taskDO.designation} date plus tôt "
       // seriePlusTot.node.style = "-fx-stroke: blue;"
        return seriePlusTot
    }
    fun createTaskSeriePluTard(taskDO: TaskDO) : Series<Number,Number >{

        var seriePlusTard : Series<Number, Number> = Series()
        val index = appViewmodel.taskDesigantionList.indexOfFirst {
            it == taskDO.designation
        }
        val indexInGantt = appViewmodel.taskDesigantionList.count()-index + 0.5

        seriePlusTard.data.add(XYChart.Data(taskDO.tempsPlusTard,indexInGantt))
        seriePlusTard.data.add(XYChart.Data(taskDO.tempsPlusTard + taskDO.duree, indexInGantt))
        seriePlusTard.name = "${taskDO.designation} date plus tard"
       // seriePlusTard.node.style = "-fx-stroke: red;"
        return seriePlusTard
    }


    fun  createSerieTotListFromManagedTasks(taskDEBUTDO: TaskDO, list : ObservableList<Series<Number, Number>>) : ObservableList<Series<Number, Number>>{

            list.add(createTaskSeriePluTot(taskDEBUTDO))
            for (task in taskDEBUTDO.successeurs) {
                createSerieTotListFromManagedTasks(task, list)
            }

        return list
    }
    fun  createSerieTardListFromManagedTasks(taskDEBUTDO: TaskDO, list : ObservableList<Series<Number, Number>>) : ObservableList<Series<Number, Number>>{

        list.add(createTaskSeriePluTard(taskDEBUTDO))
        for (task in taskDEBUTDO.successeurs) {
            createSerieTardListFromManagedTasks(task, list)
        }

        return list
    }

    fun cheminCritique() = VBox().apply {
        val taskCritique : ObservableList<String> = FXCollections.observableArrayList("DEBUT -> ")
        createCheminCritique(taskCritique)
        var critiqueString  = ""
        for (design in taskCritique) {
            critiqueString += design
        }

        alignment = Pos.CENTER
        children += Label().apply {
            text = "CHEMIN CRITIQUE : $critiqueString"
        }
        children += HBox().apply {
            alignment = Pos.CENTER_RIGHT
            children += Button("Afficher les marges")
        }

    }

    private fun createCheminCritique( taskDesignInCheminCritique : ObservableList<String>) {
        for (task in appViewmodel.taskList) {
            if (task.tempsPlusTot == task.tempsPlusTard) {
                taskDesignInCheminCritique.add("${task.designation} -> ")
            }
        }
    }

    /**
     * applique le style à chaque groupe
     */

}