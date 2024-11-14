package com.crosemont.booklique.Présentation.Historique

import com.crosemont.booklique.Présentation.Historique.Modèle
import kotlinx.coroutines.Job

class Présentateur(vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()
}