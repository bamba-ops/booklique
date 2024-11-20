package com.crosemont.booklique.Présentation.DetailLivre

import com.crosemont.booklique.Présentation.DetailLivre.Vue
import kotlinx.coroutines.Job

class Présentateur(private val vue: Vue) {

    private val modèle = Modèle()
    private var job: Job? = null

    fun initialiserLivre() {
        vue.afficherLivre(modèle.obtenirLivre()!!)
    }

    fun estFavori(isbn: String) {
        if(modèle.obtenirLivreFavori(isbn)) {
            vue.changer_isFavoris_vrai()
            vue.changer_boutton_favoris_source_true()
        }
    }

    fun basculerFavori(isbn: String){
        vue.verifier_isFavoris()
        if(vue.retourner_favori()){
            vue.changer_boutton_favoris_source_true()
            modèle.ajouterLivreFavori(isbn)
        } else {
            vue.changer_boutton_favoris_source_false()
            modèle.retirerLivreFavori(isbn)
        }
    }
}
