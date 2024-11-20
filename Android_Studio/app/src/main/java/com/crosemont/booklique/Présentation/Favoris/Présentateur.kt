package com.crosemont.booklique.Présentation.Favoris

import Livre
import com.crosemont.booklique.Présentation.Favoris.Modèle
import kotlinx.coroutines.Job

class Présentateur(val vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()

    fun chargerLivresFavoris(){
        val livres = modèle.obtenirLivresFavoris()
        vue.afficherLivresFavoris(livres)
    }

    fun ajouterLivresFavoris(livre: Livre){
        modèle.ajouterLivresFavoris(livre)
        chargerLivresFavoris()
    }

    fun ouvrirDétailsLivre(isbn: String){
        modèle.obtenirLivreFavorisParISBN(isbn)
    }

    fun retirerDesFavoris(livre: Livre){
        modèle.retirerLivreFavorisParISBN(livre.isbn)
        chargerLivresFavoris()
    }
}