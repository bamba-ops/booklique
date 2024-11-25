package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.mork_data.Data
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Présentateur(private val vue: Vue, private val modèle: Modèle = Modèle()) {
    private var job: Job? = null


    fun traiter_est_livre_favori(isbn: String): Boolean {
        return modèle.estLivreFavori(isbn)
    }

    fun traiter_ajouter_livre_favori(livre: Livre){
        modèle.ajouterLivreFavori(livre)
    }

    fun traiter_retirer_livre_favori(isbn: String){
        modèle.retirerLivreFavori(isbn)
    }

    fun traiter_obtenir_livre(isbn: String){
        modèle.obtenirLivre(isbn)
    }


    fun traiter_livres_par_nouveautes() {
        vue.afficherChargement(true)

        job = CoroutineScope(Dispatchers.Main).launch {
            val livreParNouveautes: List<Livre> = modèle.obtenirLivresParNouveautes()
            if (livreParNouveautes.isNotEmpty()) {
                vue.afficherChargement(false)
                vue.préparationAfficherLivres("Nouveautés")
                for (livre in livreParNouveautes) {
                    vue.afficherLivres(livre)
                }
            } else {
                vue.afficherChargement(false)
                vue.modifierTextRechercheParDefaut("Aucune nouveauté trouvée.")
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

    fun traiter_livres_par_genre(){
        job = CoroutineScope(Dispatchers.Main).launch {
            val livreParGenre: List<Livre> = modèle._obtenirLivresParGenre()
            if(livreParGenre.isNotEmpty()){
                vue.afficherChargement(false)
                vue.préparationAfficherLivres("Genre")
                for(livre in livreParGenre){
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


    fun lancerRecherche(rechercheTexte: String, critère: String = "titre") {
        val texte = rechercheTexte.trim()
        if (texte.isNotEmpty()) {
            when (critère) {
                "auteur" -> afficherLivresParAuteur(texte)
                "titre" -> afficherLivresParTitre(texte)
                "genre" -> afficherLivresParGenre(texte)
                else -> vue.modifierTextRechercheParDefaut("Critère inconnu.")
            }
        } else {
            vue.préparationAfficherRésultatsRecherche()
            vue.modifierTextRechercheParDefaut("Veuillez entrer un texte à rechercher.")
            vue.afficherDefilementResultatRecherche(false)
        }
    }



}
