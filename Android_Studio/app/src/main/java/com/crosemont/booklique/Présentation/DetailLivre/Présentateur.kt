package com.crosemont.booklique.Présentation.DetailLivre

import com.crosemont.booklique.Présentation.DetailLivre.Vue
import kotlinx.coroutines.Job

class Présentateur(private val vue: Vue) {

    private val modèle = Modèle()
    private var job: Job? = null

    fun initialiserLivre(isbn: String) {
        val livreFavori = modèle.obtenirLivreFavori(isbn)
        vue.mettreAJourFavoris(livreFavori)
        val disponible = modèle.estDisponible(vue.getDisponibilite())
        vue.mettreAJourDisponibilite(disponible)
    }

    fun basculerFavori(isbn: String) {
        val livreFavori = modèle.obtenirLivreFavori(isbn)
        if (livreFavori) {
            modèle.retirerLivreFavori(isbn)
            vue.mettreAJourFavoris(false)
        } else {
            modèle.ajouterLivreFavori(isbn)
            vue.mettreAJourFavoris(true)
        }
    }
}
