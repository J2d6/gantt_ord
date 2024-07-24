package com.example.demo4


import com.example.demo4.data.AppViewmodel
import com.example.demo4.ui.components.GanttChart
import com.example.demo4.ui.components.TaskDetailInput
import com.example.demo4.ui.components.TaskNumberInput
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage


class MainClass : Application() {
    val appViewmodel : AppViewmodel = AppViewmodel()
    val screenBounds = Screen.getPrimary().visualBounds
    override fun start(stage: Stage) {
        stage.scene = Scene(
            //GanttChart(appViewmodel),
            TaskNumberInput(
                appViewmodel
            ){
                _ -> stage.scene = Scene(
                        TaskDetailInput(appViewmodel) {
                            _ -> stage.scene = Scene(
                                GanttChart(appViewmodel),
                                screenBounds.width, screenBounds.height
                            )
                        },
                       screenBounds.width, screenBounds.height)
            },
            screenBounds.width, screenBounds.height
        )

        stage.isMaximized = true
        stage.title = " Ordonnancement - GANTT "
        stage.show()
    }


}

fun main() = Application.launch(MainClass::class.java)

//<>