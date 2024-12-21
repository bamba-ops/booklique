package com.crosemont.booklique.Présentation.Recherche

import Livre
import com.crosemont.booklique.domaine.entité.Favoris
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Présentateur_Resultat(private val vue: Vue_Resultat){
    private var job: Job? = null
    private val modèle = Modèle(vue.requireContext())

    fun traiter_livre_favori(isbn: String, index: Int) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val favori = withContext(Dispatchers.IO) { modèle.obtenirLivreFavori(isbn) }
            if (favori != null) {
                vue.changer_image_resource_true(index)
            } else {
                vue.changer_image_resource_false(index)
            }
        }
    }

    fun traiter_livre_favori_boutton(livre: Livre, index: Int) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val favori = withContext(Dispatchers.IO) { modèle.obtenirLivreFavori(livre.isbn) }
            if (favori != null) {
                traiter_retirer_livre_favori(livre.isbn)
                vue.changer_image_resource_false(index)
            } else {
                traiter_ajouter_livre_favori(livre)
                vue.changer_image_resource_true(index)
            }
        }
    }

    fun traiter_ajouter_livre_favori(livre: Livre) {
        val favoris = Favoris(
            livre.isbn,
            livre.image_url,
            livre.titre,
            livre.description,
            livre.auteur,
            livre.editeur,
            livre.genre
        )
        job = CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) { modèle.ajouterLivreFavori(favoris) }
        }
    }

    fun traiter_retirer_livre_favori(isbn: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) { modèle.retirerLivreFavori(isbn) }
        }
    }

    fun traiter_obtenir_livre(isbn: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) { modèle.obtenirLivre(isbn) }
        }
        vue.naviguerVersDetailLivre()
    }

    fun traiter_livre() {
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
        }else{
            vue.afficherChargement()
            job = CoroutineScope(Dispatchers.Main).launch {
                val livreParTitre =
                    withContext(Dispatchers.IO) { modèle.obtenirLivreParTitre()?.let { listOf(it) } }
                val livreParNouveautes =
                    withContext(Dispatchers.IO) { modèle.obtenirLivresParNouveautes() }
                val livreParAuteur = withContext(Dispatchers.IO) { modèle.obtenirLivresParAuteur() }
                val livreParGenre = withContext(Dispatchers.IO) { modèle._obtenirLivresParGenre() }

                when {
                    modèle.isObtenirLivreParTitre && !livreParTitre.isNullOrEmpty() ->
                        afficherLivres(livreParTitre, "Critère de recherche : Titre")

                    modèle.isObtenirLivreParNouveautes && livreParNouveautes.isNotEmpty() ->
                        afficherLivres(livreParNouveautes, "Critère de recherche : Nouveautes")

                    modèle.isObtenirLivreParAuteur && livreParAuteur.isNotEmpty() ->
                        afficherLivres(livreParAuteur, "Critère de recherche : Auteur")

                    modèle.isObtenirLivreParGenre && livreParGenre.isNotEmpty() ->
                        afficherLivres(livreParGenre, "Critère de recherche : Genre")

                    else -> afficherAucunLivreTrouvé()
                }
            }
        }
    }

    private fun afficherLivres(livres: List<Livre>, critère: String) {
        vue.enleverChargement()
        vue.modifierTextCritereRecherche(critère)
        vue.afficherTextCritereRecherche()
        traiterAffichageLivres()
        livres.forEachIndexed { index, livre ->
            vue.afficherLivres(livre, index)
        }
    }

    private fun afficherAucunLivreTrouvé() {
        vue.enleverChargement()
        vue.modifierTextRechercheParDefaut("Aucun livre trouvé.")
        vue.afficherTextParDefaut()
    }

    fun traiterAffichageLivres() {
        vue.enleverTextParDefaut()
        vue.afficherDefilementResultatRecherche()
    }
}