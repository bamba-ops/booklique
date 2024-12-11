package com.crosemont.booklique.Présentation.Favoris

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.crosemont.booklique.domaine.dao.dbConfig.DatabaseBuilder
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.service.LivreService

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
        LivreService.definirLivre(isbn)
    }

    @SuppressLint("ServiceCast")
    fun connexion(context : Context) : Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}