package com.crosemont.booklique.Présentation.Genres

import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.Présentation.Genres.Modèle
import com.crosemont.booklique.R
import kotlinx.coroutines.Job

class Présentateur(vue: Vue,private val navigationHandler: (Int) -> Unit) {
    private var job: Job? = null
    private val modèle = Modèle()

    fun traiter_obtenir_livres_par_genre(genre: String){
        modèle.obtenirLivreParGenre(genre)
        navigationHandler(R.id.action_genres_to_resultat)
    }
}