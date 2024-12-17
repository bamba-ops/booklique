package com.crosemont.booklique.Présentation.Accueil

import kotlinx.coroutines.*



class Présentateur(val vue: Vue, val modèle: Modèle = Modèle()) {
    private var job: Job? = null

    fun traiter_affichage_livre() {
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
        }else{
            job = CoroutineScope(Dispatchers.Main).launch {
                try {
                    vue.afficherChargement()
                    // Fetch data in IO thread
                    val livreAuteurList = withContext(Dispatchers.IO) {
                        modèle.obtenirLivresParAuteur()
                    }
                    val livreList = withContext(Dispatchers.IO) {
                        modèle.obtenirLivreParNouveautes()
                    }

                    livreAuteurList.forEach { livre ->
                        vue.afficherCartesAuteurs(livre) // This runs on Main
                    }
                    livreList.forEach { livre ->
                        vue.afficherListeNouveautes(livre) // This runs on Main
                    }

                    vue.afficherAccueil()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    vue.enleverChargement()
                }
            }
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