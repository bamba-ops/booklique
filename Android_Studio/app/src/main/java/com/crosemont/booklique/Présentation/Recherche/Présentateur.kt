package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Présentateur(private val vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()

    fun traiter_livres_par_nouveautes(){
        job = CoroutineScope(Dispatchers.Main).launch {
            val livreParNouveautes: List<Livre> = modèle.obtenirLivresParNouveautes()
            if(livreParNouveautes.isNotEmpty()){
                vue.afficherChargement(false)
                vue.préparationAfficherLivres("Nouveautés")
                for(livre in livreParNouveautes){
                    vue.afficherLivres(livre)
                }
            } else {
                vue.afficherChargement(false)
            }
        }
        vue.afficherChargement(true)
    }

    fun traiter_livres_par_auteur(){
        job = CoroutineScope(Dispatchers.Main).launch {
            val livreParAuteur: List<Livre> = modèle.obtenirLivresParAuteur()
            if(livreParAuteur.isNotEmpty()){
                vue.afficherChargement(false)
                vue.préparationAfficherLivres("Auteur")
                for(livre in livreParAuteur){
                    vue.afficherLivres(livre)
                }
            } else {
                vue.afficherChargement(false)
            }
        }
        vue.afficherChargement(true)

    }

    fun afficherLivresParGenre(genre: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val livres = modèle.obtenirLivresParGenre(genre)
            vue.préparationAfficherRésultatsRecherche()
            vue.modifierTextRechercheUtilisateur(genre)
            if(livres.isEmpty()){
                vue.afficherChargement(false)
                vue.afficherDefilementResultatRecherche(false)
                vue.modifierTextRechercheParDefaut("Aucun résultat trouvé pour '$genre'.")
            }else{
                vue.afficherChargement(false)
                vue.modifierTextRechercheParDefaut("Recherche : $genre")
                vue.afficherDefilementResultatRecherche(true)

                for(livre in livres){
                    vue.afficherLivres(livre)
                }
            }
        }
        vue.afficherChargement(true)

    }

    fun afficherLivresParAuteur(auteur: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val livres = modèle.obtenirLivresParAuteur(auteur)
            vue.préparationAfficherRésultatsRecherche()
            vue.modifierTextRechercheUtilisateur(auteur)
            if(livres.isEmpty()){
                vue.afficherChargement(false)
                vue.afficherDefilementResultatRecherche(false)
                vue.modifierTextRechercheParDefaut("Aucun résultat trouvé pour '$auteur'.")
            }else{
                vue.afficherChargement(false)
                vue.modifierTextRechercheParDefaut("Recherche : $auteur")
                vue.afficherDefilementResultatRecherche(true)

                for(livre in livres){
                    vue.afficherLivres(livre)
                }
            }
        }
        vue.afficherChargement(true)

    }

    fun afficherLivresParTitre(titre: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val livres = modèle.obtenirLivresParTitre(titre)
            vue.préparationAfficherRésultatsRecherche()
            vue.modifierTextRechercheUtilisateur(titre)
            if(livres.isEmpty()){
                vue.afficherChargement(false)
                vue.afficherDefilementResultatRecherche(false)
                vue.modifierTextRechercheParDefaut("Aucun résultat trouvé pour '$titre'.")
            }else{
                vue.afficherChargement(false)
                vue.modifierTextRechercheParDefaut("Recherche : $titre")
                vue.afficherDefilementResultatRecherche(true)

                for(livre in livres){
                    vue.afficherLivres(livre)
                }
            }
        }
        vue.afficherChargement(true)

    }


    fun lancerRecherche(rechercheTexte: String) {
        val texte = rechercheTexte.trim()
        if (texte.isNotEmpty()) {
            when {
                texte.startsWith("auteur:", ignoreCase = true) -> {
                    val auteurRecherche = texte.removePrefix("auteur:").trim()
                    afficherLivresParAuteur(auteurRecherche)
                }
                texte.startsWith("titre:", ignoreCase = true) -> {
                    val titreRecherche = texte.removePrefix("titre:").trim()
                    afficherLivresParTitre(titreRecherche)
                }
                texte.startsWith("genre:", ignoreCase = true) -> {
                    val genreRecherche = texte.removePrefix("genre:").trim()
                    afficherLivresParGenre(genreRecherche)
                }
                else -> {
                    afficherLivresParTitre(texte)
                }
            }
        }
    }

}
