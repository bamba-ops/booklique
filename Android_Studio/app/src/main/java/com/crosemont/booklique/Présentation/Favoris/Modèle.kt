package com.crosemont.booklique.Présentation.Favoris

import Livre
import com.crosemont.booklique.domaine.mork_data.Data

class Modèle {

    fun obtenirLivresFavoris(): List<Livre>{
        return Data.obtenirLivresFavoris()
    }

    fun obtenirLivreFavorisParISBN(isbn: String): Livre? {
       return Data.obtenirLivreFavorisParISBN(isbn)
    }

    fun ajouterLivresFavoris(livre: Livre) {
        return Data.ajouterLivreFavori(livre)
    }

    fun retirerLivreFavorisParISBN(isbn: String){
        Data.retirerLivreFavoriParISBN(isbn)
    }


}