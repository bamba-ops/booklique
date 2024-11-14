package com.crosemont.booklique.Présentation.DetailLivre

import com.crosemont.booklique.domaine.mork_data.Data

class Modèle {


    fun obtenirLivreFavori(isbn: String): Boolean {
        return Data.estLivreFavori(isbn)
    }

    fun ajouterLivreFavori(isbn: String) {
        Data.obtenirLivreParISBN(isbn)?.let { Data.ajouterLivreFavori(it) }
    }

    fun retirerLivreFavori(isbn: String) {
        Data.retirerLivreFavoriParISBN(isbn)
    }

    fun estDisponible(disponibilite: String): Boolean {
        return disponibilite == "Disponible"
    }
}