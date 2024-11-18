package com.crosemont.booklique.Présentation.Historique

import com.crosemont.booklique.domaine.mork_data.Data
import com.crosemont.booklique.domaine.entité.Reservation
import java.text.SimpleDateFormat
import java.util.*

class Présentateur(private val vue: Vue) {

    fun formaterDateHistorique(date: Date?): String {
        if (date == null) {
            return "Date invalide"
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun afficherHistoriqueReservation() {
        val historiqueList = Data.obtenirReservationsDemo().map { reservation ->
            Modèle(
                titre = reservation.livre.titre,
                dateReservation = formaterDateHistorique(reservation.debut),
                dateRetour = formaterDateHistorique(reservation.termine)
            )
        }
        vue.afficherHistoriqueReservation(historiqueList)
    }
}