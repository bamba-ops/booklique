package com.crosemont.booklique.Présentation.Favoris

import com.crosemont.booklique.Présentation.Favoris.Modèle
import kotlinx.coroutines.Job

class Présentateur(vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()
}