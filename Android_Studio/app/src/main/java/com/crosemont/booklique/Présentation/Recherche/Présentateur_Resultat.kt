package com.crosemont.booklique.Présentation.Recherche

import Livre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Présentateur_Resultat(private val vue: Vue_Resultat, private val modèle: Modèle = Modèle()){
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

    fun traiter_livre_par_titre(){
        vue.afficherChargement(true)
        job = CoroutineScope(Dispatchers.Main).launch {
            val livreParTitre: Livre? = modèle.obtenirLivreParTitre()
            if (livreParTitre != null) {
                vue.afficherChargement(false)
                vue.préparationAfficherLivres("Nouveautés")
                vue.afficherLivres(livreParTitre)
            } else {
                vue.afficherChargement(false)
                vue.modifierTextRechercheParDefaut("Aucune nouveauté trouvée.")
            }
        }
        vue.afficherChargement(true)
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
}