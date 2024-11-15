package com.crosemont.booklique.Présentation.Recherche

import Livre
import com.crosemont.booklique.domaine.mork_data.Data

class Modèle {

    private val livres: List<Livre> = Data.obtenirLivresDemo()

    fun obtenirLivresParAuteur(auteur: String): List<Livre> {
        return livres.filter { it.auteur.contains(auteur, ignoreCase = true) }
    }

    fun obtenirLivresParTitre(titre: String): List<Livre> {
        return livres.filter { it.titre.contains(titre, ignoreCase = true) }
    }

    fun obtenirLivresParGenre(genre: String): List<Livre> {
        return livres.filter { it.genre.contains(genre, ignoreCase = true) }
        }

    fun trierLivresParNouveaute(): List<Livre> {
        return this.livres.sortedByDescending { it.date_publication }
    }

    fun obtenirLivresParAuteur(): List<Livre>{
        if(Data.isObtenirLivreParAuteur){
            Data.isObtenirLivreParAuteur = false
            return Data.obtenirLivresParAuteur(Data._obtenirLivreParAuteur!!)
        }
        return emptyList()
    }

    fun obtenirLivresParNouveautes(): List<Livre> {
        if(Data.isObtenirLivresParNouveautes){
            Data.isObtenirLivresParNouveautes = false
            return Data.obtenirLivresParNouveautes()
        }
        return emptyList()
    }

    fun _obtenirLivresParGenre(): List<Livre>{
        if(Data.isObtenirLivresParGenre){
            Data.isObtenirLivresParGenre = false
            return Data.obtenirLivresParGenre(Data._obtenirLivresParGenre!!)
        }
        return emptyList()
    }
}