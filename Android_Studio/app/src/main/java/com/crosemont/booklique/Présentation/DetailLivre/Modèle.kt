package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import android.content.Context
import com.crosemont.booklique.sourcededonnées.dao.dbConfig.DatabaseBuilder
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.entité.ReservationHistorique
import com.crosemont.booklique.domaine.service.LivreService
import com.crosemont.booklique.domaine.service.ReservationService

class Modèle(context: Context) {

    private val database = DatabaseBuilder.obtenirInstance(context)
    private val favorisDao = database.favorisDao()
    private val reservationDao = database.reservationHistoriqueDao()

    suspend fun obtenirLivreFavori(isbn: String): Favoris? {
        return favorisDao.obtenirFavoriParIsbn(isbn)
    }

    suspend fun ajouterLivreFavori(favori: Favoris) {
        favorisDao.ajouterFavori(favori)
    }

    suspend fun retirerLivreFavori(isbn: String) {
        favorisDao.supprimerFavoriParIsbn(isbn)
    }

    fun modifierLivreParIsbn(isbn: String, livre: Livre): Livre?{
        return LivreService.modifierLivreParIsbn(isbn, livre)
    }

    fun ajouterReservation(reservation: Reservation): Reservation? {
        return ReservationService.ajouterReservation(reservation)
    }

    suspend fun ajouterReservationHistorique(reservationHistorique: ReservationHistorique){
        reservationDao.ajouterReservationHistorique(reservationHistorique)
    }

    fun estDisponible(disponibilite: String): Boolean {
        return disponibilite == "Disponible"
    }

    fun obtenirLivre(): Livre?{
        if(LivreService.isObtenirLivre){
            return LivreService.obtenirLivre(LivreService._obtenirLivre!!)
        }
        return null
    }

}