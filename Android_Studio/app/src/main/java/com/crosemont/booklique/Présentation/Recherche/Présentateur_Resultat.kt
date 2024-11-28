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

    fun traiter_livre() {
        vue.afficherChargement(true)

        job = CoroutineScope(Dispatchers.Main).launch {
            val livreParTitre = modèle.obtenirLivreParTitre()?.let { listOf(it) }
            val livreParNouveautes = modèle.obtenirLivresParNouveautes()
            val livreParAuteur = modèle.obtenirLivresParAuteur()
            val livreParGenre = modèle._obtenirLivresParGenre()

            when {
                modèle.isObtenirLivreParTitre && !livreParTitre.isNullOrEmpty() -> afficherLivres(livreParTitre, "Critère de recherche : Titre")
                modèle.isObtenirLivreParNouveautes && livreParNouveautes.isNotEmpty() -> afficherLivres(livreParNouveautes, "Critère de recherche : Nouveautes")
                modèle.isObtenirLivreParAuteur && livreParAuteur.isNotEmpty() -> afficherLivres(livreParAuteur, "Critère de recherche : Auteur")
                modèle.isObtenirLivreParGenre && livreParGenre.isNotEmpty() -> afficherLivres(livreParGenre, "Critère de recherche : Genre")
                else -> afficherAucunLivreTrouvé()
            }
        }
    }

    private fun afficherLivres(livres: List<Livre>, critère: String) {
        vue.afficherChargement(false)
        vue.modifierTextCritereRecherche(critère)
        vue.afficherTextCritereRecherche(true)
        vue.préparationAfficherLivres()
        livres.forEach { vue.afficherLivres(it) }
    }

    private fun afficherAucunLivreTrouvé() {
        vue.afficherChargement(false)
        vue.modifierTextRechercheParDefaut("Aucun livre trouvé.")
        vue.afficherTextParDefaut(true)
    }


}