package com.crosemont.booklique.Présentation.Accueil

import android.view.LayoutInflater
import kotlinx.coroutines.*
import com.crosemont.booklique.Présentation.Accueil.Modèle
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso


class Présentateur(val vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()

    fun traiter_affichage_livre() {
        job = CoroutineScope( Dispatchers.Main ).launch {
            for(livre in modèle.obtenirLivresParAuteur()){
                vue.afficherCartesAuteurs(livre)
            }
            for(livre in modèle.obtenirLivres()){
                vue.afficherListeNouveautes(livre)
            }
            vue.afficherChargement(false)
            vue.afficherAccueil(true)
        }
    }

    fun traiter_obtenir_livres_par_auteur(auteur: String){
        modèle.obtenirLivreParAuteur(auteur)
    }

    fun traiter_obtenir_livres_par_nouveautes(){
        modèle.obtenirLivresParNouveautes()
    }

    fun traiter_obtenir_livre_par_nouveaute(isbn: String){
        modèle.obtenirLivreParNouveaute(isbn)
    }

}