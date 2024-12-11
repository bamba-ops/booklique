package com.crosemont.booklique.Présentation.Accueil

import Livre
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.crosemont.booklique.domaine.service.LivreService

class Modèle() {

    fun obtenirLivreParNouveautes(): List<Livre>{
        return LivreService.obtenirLivresParNouveautesPrend5()
    }

    fun obtenirLivresParAuteur(): List<Livre>{
        return LivreService.obtenirLivresParAuteur()
    }

    fun obtenirLivreParAuteur(auteur: String){
        LivreService.definirLivreParAuteur(auteur)
    }

    fun obtenirLivreParNouveaute(isbn: String){
        LivreService.definirLivre(isbn)
    }

    fun obtenirLivresParNouveautes(){
        LivreService.definirLivresParNouveautes()
    }

    @SuppressLint("ServiceCast")
    fun connexion(context : Context) : Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}