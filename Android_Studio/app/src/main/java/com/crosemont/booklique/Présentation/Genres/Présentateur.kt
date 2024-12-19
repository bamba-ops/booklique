package com.crosemont.booklique.Présentation.Genres

import kotlinx.coroutines.Job

class Présentateur(vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()

    fun traiter_obtenir_livres_par_genre(genre: String){
        modèle.obtenirLivreParGenre(genre)
    }
}