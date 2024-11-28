package com.crosemont.booklique.Présentation.Recherche

import Livre
import com.crosemont.booklique.domaine.mork_data.Data

class Modèle {

    var isObtenirLivreParAuteur: Boolean = false
    var isObtenirLivreParTitre: Boolean = false
    var isObtenirLivreParNouveautes: Boolean = false
    var isObtenirLivreParGenre: Boolean = false

    fun obtenirLivresParNomAuteur(auteur: String){
        Data.definirLivreParAuteur(auteur)
    }

    fun obtenirLivresParNomTitre(titre: String){
        Data.definirLivreParTitre(titre)
    }

    fun obtenirLivresParTitres(): List<String>{
        return Data.obtenirLivresParTires()
    }

    fun obtenirLivresParAuteursListString(): List<String>{
        return Data.obtenirLivresParAuteursListString()
    }

    fun obtenirLivresParAuteur(): List<Livre>{
        if(Data.isObtenirLivreParAuteur){
            Data.isObtenirLivreParAuteur = false
            isObtenirLivreParAuteur = true
            return Data.obtenirLivresParAuteur(Data._obtenirLivreParAuteur!!)
        }
        return emptyList()
    }

    fun obtenirLivreParTitre(): Livre? {
        if(Data.isObtenirLivreParTitre){
            Data.isObtenirLivreParTitre = false
            isObtenirLivreParTitre = true
            return Data.obtenirLivreParTitre(Data._obtenirLivreParTitre!!)
        }

        return null
    }

     fun obtenirLivresParNouveautes(): List<Livre> {
        if (Data.isObtenirLivresParNouveautes) {
            Data.isObtenirLivresParNouveautes = false
            isObtenirLivreParNouveautes = true
            return Data.obtenirLivresDemo()
        }
        return emptyList()
    }


    fun _obtenirLivresParGenre(): List<Livre>{
        if(Data.isObtenirLivresParGenre){
            Data.isObtenirLivresParGenre = false
            isObtenirLivreParGenre = true
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



}