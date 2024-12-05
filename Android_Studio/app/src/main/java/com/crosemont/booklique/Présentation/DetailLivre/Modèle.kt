package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import android.content.Context
import com.crosemont.booklique.domaine.dao.dbConfig.DatabaseBuilder
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.service.LivreService

class Modèle(context: Context) {

    private val database = DatabaseBuilder.obtenirInstance(context)
    private val favorisDao = database.favorisDao()

    suspend fun obtenirLivreFavori(isbn: String): Favoris? {
        return favorisDao.obtenirFavoriParIsbn(isbn)
    }

    suspend fun ajouterLivreFavori(favori: Favoris) {
        favorisDao.ajouterFavori(favori)
    }

    suspend fun retirerLivreFavori(isbn: String) {
        favorisDao.supprimerFavoriParIsbn(isbn)
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