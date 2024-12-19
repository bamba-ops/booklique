package com.crosemont.booklique.Présentation.Genres

import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.Présentation.Genres.Modèle
import com.crosemont.booklique.R
import kotlinx.coroutines.Job

class Présentateur( private val vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()

    fun traiter_obtenir_livres_par_genre(genre: String){
        modèle.obtenirLivreParGenre(genre)
        vue.naviguerVersResultats()

    }
}