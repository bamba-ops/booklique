package com.crosemont.booklique.Présentation.Accueil

import android.view.LayoutInflater
import kotlinx.coroutines.*
import com.crosemont.booklique.Présentation.Accueil.Modèle
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso
import kotlin.coroutines.CoroutineContext


class Présentateur(val vue: Vue, val modèle: Modèle = Modèle()) {
    private var job: Job? = null
    //private val modèle = Modèle()

    fun traiter_affichage_livre() {
        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                vue.afficherChargement(true)

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

                vue.afficherAccueil(true)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                vue.afficherChargement(false)
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