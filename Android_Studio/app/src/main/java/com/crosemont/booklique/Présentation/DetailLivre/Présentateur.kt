package com.crosemont.booklique.Présentation.DetailLivre

import com.crosemont.booklique.Présentation.DetailLivre.Modèle
import kotlinx.coroutines.Job

class Présentateur(vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()
}