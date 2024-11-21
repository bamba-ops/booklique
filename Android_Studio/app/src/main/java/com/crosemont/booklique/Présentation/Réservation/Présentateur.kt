package com.crosemont.booklique.Présentation.Réservation

import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import com.crosemont.booklique.Presentation.Reservation.Vue
import com.crosemont.booklique.Présentation.Réservation.Modèle
import kotlinx.coroutines.Job
import java.util.Calendar

class Présentateur(vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()

}