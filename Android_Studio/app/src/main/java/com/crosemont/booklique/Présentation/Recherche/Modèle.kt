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
}