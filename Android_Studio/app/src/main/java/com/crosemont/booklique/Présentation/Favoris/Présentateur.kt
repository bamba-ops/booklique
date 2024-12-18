package com.crosemont.booklique.Présentation.Favoris

import android.content.Context
import android.widget.ImageView
import com.crosemont.booklique.domaine.entité.Favoris
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Présentateur(val vue: Vue, context: Context) {
    private var job: Job? = null
    private val modèle = Modèle(context)

    fun chargerLivresFavoris(){
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
        }else{
            job = CoroutineScope( Dispatchers.Main ).launch {
                val favoris = modèle.obtenirLivresFavoris()
                if (favoris.isNotEmpty()) {
                    vue.enlever_text_view()
                    vue.charger_affichage_livre_favoris()
                    for (favori in favoris) {
                        vue.afficherLivresFavoris(favori)
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
    }

    fun traiter_favoris(favoris: Favoris, iconFavoris: ImageView){
        job = CoroutineScope( Dispatchers.Main ).launch {
            val favori = modèle.obtenirLivreFavorisParISBN(favoris.isbn)
            if(favori != null){
                modèle.retirerLivreFavorisParISBN(favoris.isbn)
                vue.changer_resource_iconeFavoris_false(iconFavoris)
                chargerLivresFavoris()
            }
        }
    }
}