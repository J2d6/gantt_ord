package com.example.demo4.services.taskServices

import javafx.collections.FXCollections
import javafx.collections.ObservableList

fun calculateTempsPlusTot(debut: TaskDO) {
    println("IN CALCUL PLUS TOT")
    fun dfs(tache: TaskDO) {
        if (!tache.successeurs.isEmpty()) {
            for (tacheSuperieure in tache.successeurs) {
                println("tache en cours : ${tacheSuperieure.designation}")
                val nouvelleDateAuPlusTot = tache.tempsPlusTot + tache.duree
                if (nouvelleDateAuPlusTot > tacheSuperieure.tempsPlusTot) {
                    tacheSuperieure.tempsPlusTot = nouvelleDateAuPlusTot
                }
                dfs(tacheSuperieure)
            }
        }

    }

    debut.tempsPlusTot = 0
    dfs(debut)
}
fun calculateTempsPlusTard(fin: TaskDO) {
    println("IN CALCUL PLUS TARD")
    fun dfs(tache: TaskDO) {
        for (tacheAnterieure in tache.antecedents) {
            val nouvelleDateAuPlusTard = tache.tempsPlusTard - tacheAnterieure.duree
            if (tacheAnterieure.tempsPlusTard == 0) {
                if(tacheAnterieure.designation != "DEBUT") {
                    tacheAnterieure.tempsPlusTard = nouvelleDateAuPlusTard
                }
            } else {
                if (nouvelleDateAuPlusTard < tacheAnterieure.tempsPlusTard) {
                    tacheAnterieure.tempsPlusTard = nouvelleDateAuPlusTard
                }
            }
            dfs(tacheAnterieure)
        }
    }

    fin.tempsPlusTard = fin.tempsPlusTot
    dfs(fin)
}


