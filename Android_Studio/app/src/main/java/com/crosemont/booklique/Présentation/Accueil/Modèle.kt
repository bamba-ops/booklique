package com.crosemont.booklique.Présentation.Accueil

import Livre
import com.crosemont.booklique.domaine.mork_data.Data
import kotlinx.coroutines.delay

class Modèle {

    suspend fun obtenirLivres(): List<Livre>{
        delay(600)
        return Data.obtenirLivresDemo()
    }

    suspend fun obtenirLivreParNouveautes(): List<Livre>{
        delay(600)
        return Data.obtenirLivresDemo()
    }

    suspend fun obtenirLivresParAuteur(): List<Livre>{
        delay(600)
        return Data.obtenirLivresParAuteurs()
    }

    fun obtenirLivreParAuteur(auteur: String){
        Data.definirLivreParAuteur(auteur)
    }

    fun obtenirLivreParNouveaute(isbn: String){
        Data.definirLivre(isbn)
    }

    fun obtenirLivresParNouveautes(){
        Data.definirLivresParNouveautes()
    }

}