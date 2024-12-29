package com.crosemont.booklique.Présentation.Accueil

import Livre
import com.crosemont.booklique.domaine.service.LivreService

class Modèle() {

    fun obtenirLivreParNouveautes(): List<Livre>{
        return LivreService.obtenirLivresParNouveautesPrend5()
    }

    fun obtenirLivresParAuteur(): List<Livre>{
        return LivreService.obtenirLivresParAuteur()
    }

    fun obtenirLivreParAuteur(auteur: String){
        LivreService.definirLivreParAuteur(auteur)
    }

    fun obtenirLivreParNouveaute(isbn: String){
        LivreService.definirLivre(isbn)
    }

    fun obtenirLivresParNouveautes(){
        LivreService.definirLivresParNouveautes()
    }


}