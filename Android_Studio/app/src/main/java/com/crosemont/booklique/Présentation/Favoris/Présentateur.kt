package com.crosemont.booklique.Présentation.Favoris

import com.crosemont.booklique.domaine.entité.Favoris
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Présentateur(val vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle(vue.requireContext())

    fun chargerLivresFavoris(){
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
        }else{
            job = CoroutineScope( Dispatchers.Main ).launch {
                val favoris = modèle.obtenirLivresFavoris()
                var i = 0
                if (favoris.isNotEmpty()) {
                    vue.enlever_text_view()
                    vue.charger_affichage_livre_favoris()
                    for (favori in favoris) {
                        vue.afficherLivresFavoris(favori, i)
                        i += 1
                    }
                } else {
                    vue.charger_affichage_livre_favoris()
                    vue.afficher_text_view()
                }
            }
        }
    }

    fun traiter_obtenir_livre(isbn: String){
        modèle.obtenirLivre(isbn)
        vue.naviguerVersDetailLivre()
    }

    fun traiter_favoris(favoris: Favoris, index: Int){
        job = CoroutineScope( Dispatchers.Main ).launch {
            val favori = modèle.obtenirLivreFavorisParISBN(favoris.isbn)
            if(favori != null){
                modèle.retirerLivreFavorisParISBN(favoris.isbn)
                vue.changer_resource_iconeFavoris_false(index)
                chargerLivresFavoris()
            }
        }
    }
}