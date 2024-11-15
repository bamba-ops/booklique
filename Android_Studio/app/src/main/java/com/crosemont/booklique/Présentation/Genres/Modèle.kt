package com.crosemont.booklique.Présentation.Genres

import com.crosemont.booklique.domaine.mork_data.Data

class Modèle {

    fun obtenirLivreParGenre(genre: String){
        Data.definirLivreParGenre(genre)
    }
}