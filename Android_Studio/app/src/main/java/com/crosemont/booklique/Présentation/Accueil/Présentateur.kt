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
        if(!modèle.connexion(vue.requireContext())){
            traiterConnexion(vue.requireContext())
        }else{
            job = CoroutineScope(Dispatchers.Main).launch {
                try {
                    vue.afficherChargement(true)

                    val livreAuteurList = withContext(Dispatchers.IO) {
                        modèle.obtenirLivresParAuteur()
                    }
                    val livreList = withContext(Dispatchers.IO) {
                        modèle.obtenirLivreParNouveautes()
                    }

                    livreAuteurList.forEach { livre ->
                        vue.afficher_livre_par_auteur(livre)
                    }
                    livreList.forEach { livre ->
                        vue.afficher_livre_par_nouveautes(livre)
                    }

                    vue.afficherAccueil(true)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    vue.afficherChargement(false)
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

    fun traiterConnexion(context : Context){
        AlertDialog.Builder(context)
            .setTitle("Connexion internet perdue")
            .setMessage("Veuillez vous reconnecter")
            .setNegativeButton("OK"){
                                    dialog, which -> dialog.dismiss()
            }.show()
    }
}