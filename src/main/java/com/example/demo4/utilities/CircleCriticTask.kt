package com.example.demo4.utilities

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Text

fun criticTasksContainer(criticTasksDesignation : List<String>) : HBox = HBox().apply {
    val criticTasksCircles : ObservableList<HBox> = createCriticTasksCircles(criticTasksDesignation)
    return HBox().apply {
        children += criticTasksCircles
        alignment = Pos.CENTER
    }
}

fun createCriticTasksCircles(criticTasksDesignation : List<String>): ObservableList<HBox> {
    var circles : ObservableList<HBox> = FXCollections.observableArrayList()

    for (taskDesignation in criticTasksDesignation) {
        var hbox : HBox = HBox()
        val circle = Circle(15.0) // rayon de 50, donc diamètre 100
        circle.fill = Color.RED // couleur de fond du cercle

        // Créer un texte avec une seule lettre
        val letter = Text(taskDesignation) // lettre à afficher
        letter.fill = Color.WHITE // couleur du texte
        letter.style = "-fx-font-size: 9; -fx-font-weight: bold;" // taille de la police
        // Utiliser un StackPane pour centrer le texte dans le cercle
        val stackPane = StackPane()
        stackPane.children.addAll(circle, letter)
        val arrowBox = StackPane().apply {
            children +=Label(" -> ")
        }

        hbox.children.addAll(stackPane, arrowBox)
        circles.add(hbox)
    }

    return circles

}
