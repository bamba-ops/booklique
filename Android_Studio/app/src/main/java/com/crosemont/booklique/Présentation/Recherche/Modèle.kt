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
        return livres.filter { it.genre.trim().equals(genre.trim(), ignoreCase = true) }
    }


    fun obtenirLivresParAuteur(): List<Livre>{
        if(Data.isObtenirLivreParAuteur){
            Data.isObtenirLivreParAuteur = false
            return Data.obtenirLivresParAuteur(Data._obtenirLivreParAuteur!!)
        }
        return emptyList()
    }

     fun obtenirLivresParNouveautes(): List<Livre> {
        if (Data.isObtenirLivresParNouveautes) {
            Data.isObtenirLivresParNouveautes = false
            return Data.obtenirLivresDemo()
        }
        return emptyList()
    }

<<<<<<< HEAD
    fun _obtenirLivresParGenre(): List<Livre>{
        if(Data.isObtenirLivresParGenre){
            Data.isObtenirLivresParGenre = false
            return Data.obtenirLivresParGenre(Data._obtenirLivresParGenre!!)
        }
        return emptyList()
    }

    fun estLivreFavori(isbn: String):Boolean{
        return Data.estLivreFavori(isbn)
    }

    fun ajouterLivreFavori(livre: Livre){
        Data.ajouterLivreFavori(livre)
    }

    fun retirerLivreFavori(isbn: String){
        Data.retirerLivreFavoriParISBN(isbn)
    }

    fun obtenirLivre(isbn: String){
        Data.definirLivre(isbn)
    }
=======

>>>>>>> mvpRecherche
}