package com.crosemont.booklique.Présentation.Accueil

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.*
import com.crosemont.booklique.Présentation.Accueil.Modèle
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso
import kotlin.coroutines.CoroutineContext


class Présentateur(val vue: Vue, val modèle: Modèle = Modèle()) {
    private var job: Job? = null
    //private val modèle = Modèle()

    fun traiter_affichage_livre() {
        if (!modèle.connexion(vue.requireContext())) {
            traiterConnexion(vue.requireContext())
        } else {
            job = CoroutineScope(Dispatchers.Main).launch {
                vue.afficherChargement(true)

                val livreAuteurList = withContext(Dispatchers.IO) {
                    modèle.obtenirLivresParAuteur()
                }
                val livreList = withContext(Dispatchers.IO) {
                    modèle.obtenirLivreParNouveautes()
                }

                livreAuteurList.forEach { livre ->
                    vue.afficherCartesAuteurs(livre)// This runs on Main
                }
                livreList.forEach { livre ->
                    vue.afficherListeNouveautes(livre) // This runs on Main
                }

                vue.afficherAccueil(true)
                vue.afficherChargement(false)
            }
        }
    }

    fun traiter_naviguer_genres() {
        vue.naviguerVersGenres()
    }

    fun traiter_obtenir_livres_par_auteur(auteur: String){
        modèle.obtenirLivreParAuteur(auteur)
        vue.naviguerVersResultatsAvecAuteur(auteur)
    }

    fun traiter_obtenir_livres_par_nouveautes(){
        modèle.obtenirLivresParNouveautes()
        vue.naviguerVersResultatsAvecNouveautes()
    }

    fun traiter_obtenir_livre_par_nouveaute(isbn: String){
        modèle.obtenirLivreParNouveaute(isbn)
        vue.naviguerVersDetailLivre(isbn)
    }

    fun traiterConnexion(context : Context){
        AlertDialog.Builder(context)
            .setTitle("Connexion internet perdue")
            .setMessage("Veuillez vous reconnecter")
            .setNegativeButton("OK"){
                                    dialog, which -> dialog.dismiss()
            }.show()
    }
}