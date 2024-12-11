package com.crosemont.booklique.Présentation.Genres

import com.crosemont.booklique.domaine.service.LivreService

class Modèle {

    fun obtenirLivreParGenre(genre: String){
        LivreService.definirLivreParGenre(genre)
    }
}