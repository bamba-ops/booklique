package com.crosemont.booklique.domaine.service

import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.sourcededonnées.SourceDeDonnéesReservationHTTP

class ReservationService {

    companion object{

        private val source_api = SourceDeDonnéesReservationHTTP()

        fun obtenirReservations(): List<Reservation>{
            return source_api.obtenirToutesReservations()
        }

        fun ajouterReservation(reservation: Reservation): Reservation? {
            return source_api.ajouterReservation(reservation)
        }

    }
}