package com.crosemont.booklique.Présentation.Historique

import Livre
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.crosemont.booklique.domaine.dao.dbConfig.DatabaseBuilder
import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.entité.ReservationHistorique
import com.crosemont.booklique.domaine.service.LivreService
import com.crosemont.booklique.domaine.service.ReservationService

class Modèle(context: Context){

    private val database = DatabaseBuilder.obtenirInstance(context)
    private val reservationHistoriqueDao = database.reservationHistoriqueDao()

    suspend fun obtenirHistoriqueReservation(): List<ReservationHistorique> {
        return reservationHistoriqueDao.obtenirToutesLesReservations()
    }

    suspend fun supprimerHistoriqueReservation(){
        reservationHistoriqueDao.supprimerToutesLesReservations()
    }

    fun obtenirLivreParIsbn(isbn: String): Livre? {
        return LivreService.obtenirLivreParISBN(isbn)
    }

    fun obtenirReservation(): List<Reservation> {
        return ReservationService.obtenirReservations()
    }

    @SuppressLint("ServiceCast")
    fun connexion(context : Context) : Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}