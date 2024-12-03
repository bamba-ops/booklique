package com.crosemont.booklique.Présentation.Favoris

import Livre
import android.content.Context
import android.provider.ContactsContract
import com.crosemont.booklique.domaine.dao.dbConfig.DatabaseBuilder
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.mork_data.Data

class Modèle(context: Context) {

    private val database = DatabaseBuilder.obtenirInstance(context)
    private val favorisDao = database.favorisDao()

    suspend fun obtenirLivresFavoris(): List<Favoris>{
        return favorisDao.obtenirTousLesFavoris()
    }

    suspend fun obtenirLivreFavorisParISBN(isbn: String): Favoris? {
        return favorisDao.obtenirFavoriParIsbn(isbn)
    }

    suspend fun ajouterLivresFavoris(favoris: Favoris) {
        return favorisDao.ajouterFavori(favoris)
    }

    suspend fun retirerLivreFavorisParISBN(isbn: String){
        favorisDao.supprimerFavoriParIsbn(isbn)
    }

    fun obtenirLivre(isbn: String){
        Data.definirLivre(isbn)
    }


}