package com.crosemont.booklique.Présentation.Réservation

import com.crosemont.booklique.Presentation.Reservation.Vue
import com.crosemont.booklique.Présentation.Réservation.Modèle
import kotlinx.coroutines.Job

class Présentateur(vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()
}