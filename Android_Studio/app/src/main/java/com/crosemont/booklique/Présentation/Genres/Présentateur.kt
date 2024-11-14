package com.crosemont.booklique.Présentation.Genres

import com.crosemont.booklique.Présentation.Genres.Modèle
import kotlinx.coroutines.Job

class Présentateur(vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()
}