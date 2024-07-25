package com.example.demo4.services.taskServices

fun calculerMarges(taches: List<TaskDO>) {
    for (tache in taches) {
        // Calculer la marge totale
        tache.margeTotale = tache.tempsPlusTard - tache.tempsPlusTot

        // Calculer la marge libre
        val dateAuPlusTotSuccesseurs = tache.successeurs
            .map { it.tempsPlusTot }
            .minOrNull() ?: Int.MAX_VALUE // Prendre la date au plus tôt du successeur, ou Int.MAX_VALUE si aucun

        tache.margeLibre = dateAuPlusTotSuccesseurs - tache.tempsPlusTard - tache.duree
    }
}
