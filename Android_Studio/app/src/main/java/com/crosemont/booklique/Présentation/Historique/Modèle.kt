package com.crosemont.booklique.Présentation.Historique

import Livre
import android.content.Context
import com.crosemont.booklique.domaine.dao.dbConfig.DatabaseBuilder
import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.entité.ReservationHistorique
import com.crosemont.booklique.domaine.mork_data.Data

class Modèle(context: Context){

    private val database = DatabaseBuilder.obtenirInstance(context)
    private val reservationHistoriqueDao = database.reservationHistoriqueDao()

    suspend fun obtenirHistoriqueReservation(): List<ReservationHistorique> {
        return reservationHistoriqueDao.obtenirToutesLesReservations()
    }

    suspend fun ajouterHistoriqueReservation(reservationHistorique: ReservationHistorique){
        reservationHistoriqueDao.ajouterReservationHistorique(reservationHistorique)
    }

    suspend fun supprimerHistoriqueReservation(){
        reservationHistoriqueDao.supprimerToutesLesReservations()
    }

    fun obtenirLivreParIsbn(isbn: String): Livre? {
        return Data.obtenirLivreParISBN(isbn)
    }

    fun obtenirReservation(): List<Reservation> {
        return Data.obtenirReservationsDemo()
    }

}