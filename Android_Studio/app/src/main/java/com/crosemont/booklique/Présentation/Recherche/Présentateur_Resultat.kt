package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.content.Context
import android.widget.ImageView
import com.crosemont.booklique.Présentation.Recherche.Modèle
import com.crosemont.booklique.domaine.entité.Favoris
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Présentateur_Resultat(private val vue: Vue_Resultat, context: Context){
    private var job: Job? = null
    private val modèle = Modèle(context)

    fun traiter_livre_favori(isbn: String, iconFavori:ImageView){
        job = CoroutineScope( Dispatchers.Main ).launch {
            var favori = modèle.obtenirLivreFavori(isbn)
            if(favori != null){
                vue.changer_image_resource_true(iconFavori)
            } else {
                vue.changer_image_resource_false(iconFavori)
            }
        }
    }

    fun traiter_livre_favori_boutton(livre: Livre, iconFavori:ImageView){
        job = CoroutineScope( Dispatchers.Main ).launch {
            job = CoroutineScope( Dispatchers.Main ).launch {
                var favori = modèle.obtenirLivreFavori(livre.isbn)
                if(favori != null){
                    traiter_retirer_livre_favori(livre.isbn)
                    vue.changer_image_resource_false(iconFavori)
                } else {
                    traiter_ajouter_livre_favori(livre)
                    vue.changer_image_resource_true(iconFavori)
                }
            }
        }
    }


    fun traiter_ajouter_livre_favori(livre: Livre){
        var favoris: Favoris = Favoris(
            livre.isbn,
            livre.image_url,
            livre.titre,
            livre.description,
            livre.auteur,
            livre.editeur,
            livre.genre
        )
        job = CoroutineScope( Dispatchers.Main ).launch {
            modèle.ajouterLivreFavori(favoris)
        }
    }

    fun traiter_retirer_livre_favori(isbn: String){
        job = CoroutineScope( Dispatchers.Main ).launch{
            modèle.retirerLivreFavori(isbn)
        }
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