package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.crosemont.booklique.domaine.dao.dbConfig.DatabaseBuilder
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.entité.Recherche
import com.crosemont.booklique.domaine.service.LivreService

class Modèle(context: Context) {

    private val favorisDao = DatabaseBuilder.obtenirInstance(context).favorisDao()
    private val rechercheDao = DatabaseBuilder.obtenirInstance(context).rechercheDao()

    var isObtenirLivreParAuteur: Boolean = false
    var isObtenirLivreParTitre: Boolean = false
    var isObtenirLivreParNouveautes: Boolean = false
    var isObtenirLivreParGenre: Boolean = false

    suspend fun supprimerHistoriqueRecherche(){
        rechercheDao.supprimerTout()
    }

    suspend fun obtenirHistoriqueRecherches(): List<String> {
        return rechercheDao.obtenirHistoriqueRecherches()
    }

    suspend fun ajouterRecherche(requete:String){
        rechercheDao.ajouterRecherche(Recherche(requete = requete))
    }

    fun obtenirLivresParNomAuteur(auteur: String){
        LivreService.definirLivreParAuteur(auteur)
    }

    fun obtenirLivresParNomTitre(titre: String){
        LivreService.definirLivreParTitre(titre)
    }

    fun obtenirLivresParTitres(): List<String>{
        return LivreService.obtenirLivresParTires()
    }

    fun obtenirLivresParAuteursListString(): List<String>{
        return LivreService.obtenirLivresParAuteursListString()
    }

    fun obtenirLivresParAuteur(): List<Livre>{
        if(LivreService.isObtenirLivreParAuteur){
            LivreService.isObtenirLivreParAuteur = false
            isObtenirLivreParAuteur = true
            return LivreService.obtenirLivresParAuteur(LivreService._obtenirLivreParAuteur!!)
        }
        return emptyList()
    }

    fun obtenirLivreParTitre(): Livre? {
        if(LivreService.isObtenirLivreParTitre){
            LivreService.isObtenirLivreParTitre = false
            isObtenirLivreParTitre = true
            return LivreService.obtenirLivreParTitre(LivreService._obtenirLivreParTitre!!)
        }

        return null
    }

     fun obtenirLivresParNouveautes(): List<Livre> {
        if (LivreService.isObtenirLivresParNouveautes) {
            LivreService.isObtenirLivresParNouveautes = false
            isObtenirLivreParNouveautes = true
            return LivreService.obtenirLivresParNouveautesPrend10()
        }
        return emptyList()
    }


    fun _obtenirLivresParGenre(): List<Livre>{
        if(LivreService.isObtenirLivresParGenre){
            LivreService.isObtenirLivresParGenre = false
            isObtenirLivreParGenre = true
            return LivreService.obtenirLivresParGenre(LivreService._obtenirLivresParGenre!!)
        }
        return emptyList()
    }

    suspend fun obtenirLivreFavori(isbn: String): Favoris? {
        return favorisDao.obtenirFavoriParIsbn(isbn)
    }

    suspend fun ajouterLivreFavori(favori: Favoris){
        favorisDao.ajouterFavori(favori)
    }

    suspend fun retirerLivreFavori(isbn: String){
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