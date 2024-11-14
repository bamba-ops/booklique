package com.crosemont.booklique.Présentation.Recherche

import com.crosemont.booklique.Présentation.Recherche.Modèle
import kotlinx.coroutines.Job

class Présentateur(vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()
}