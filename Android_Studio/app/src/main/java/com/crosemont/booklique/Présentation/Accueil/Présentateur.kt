package com.crosemont.booklique.Présentation.Accueil

import kotlinx.coroutines.*

class Présentateur(val vue: Vue, val modèle: Modèle = Modèle()) {
    private var job: Job? = null

    fun traiter_affichage_livre() {
        if (vue.connexion()) {
            vue.afficherDialogueConnexion()
        } else {
            job = CoroutineScope(Dispatchers.Main).launch {
                vue.afficherChargement()

                val livreAuteurList = withContext(Dispatchers.IO) {
                    modèle.obtenirLivresParAuteur()
                }
                val livreList = withContext(Dispatchers.IO) {
                    modèle.obtenirLivreParNouveautes()
                }

                livreAuteurList.forEach { livre ->
                    vue.afficher_livre_par_auteur(livre)// This runs on Main
                }
                livreList.forEach { livre ->
                    vue.afficher_livre_par_nouveautes(livre) // This runs on Main
                }

                vue.afficherAccueil()
                vue.afficherChargement()
            }
        }
    }

    fun traiter_naviguer_genres() {
        vue.naviguerVersGenres()
    }

    fun traiter_obtenir_livres_par_auteur(auteur: String){
        modèle.obtenirLivreParAuteur(auteur)
        vue.naviguerVersResultatsAvecAuteur()
    }

    fun traiter_obtenir_livres_par_nouveautes(){
        modèle.obtenirLivresParNouveautes()
        vue.naviguerVersResultatsAvecNouveautes()
    }

    fun traiter_obtenir_livre_par_nouveaute(isbn: String){
        modèle.obtenirLivreParNouveaute(isbn)
        vue.naviguerVersDetailLivre(isbn)
    }
}