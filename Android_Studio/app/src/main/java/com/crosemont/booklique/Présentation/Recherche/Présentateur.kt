package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.Présentation.Recherche.Modèle
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.mork_data.Data
import com.google.android.material.tabs.TabLayout.Mode
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Présentateur(private val vue: Vue, context: Context) {
    private var job: Job? = null
    private val modèle = Modèle(context)

    fun traiter_historique_recherche() {
        if(!modèle.connexion(vue.requireContext())){
            traiterConnexion(vue.requireContext())
        }else{
            job = CoroutineScope(Dispatchers.Main).launch {
                val historique =
                    withContext(Dispatchers.IO) { modèle.obtenirHistoriqueRecherches() }
                val suggestionsAvecIcones = historique.map { "⏳ $it" }
                vue.mettreAJourSuggestions(suggestionsAvecIcones)
            }
        }
    }

    fun traiter_supprimer_recherche_historique() {
        job = CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) { modèle.supprimerHistoriqueRecherche() }
            vue.mettreAJourSuggestions(emptyList())
        }
    }

    fun traiter_mise_a_jour_suggestions(critère: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val historique = withContext(Dispatchers.IO) { modèle.obtenirHistoriqueRecherches() }
            val suggestions = withContext(Dispatchers.IO) {
                if (critère == "titre") {
                    modèle.obtenirLivresParTitres()
                } else {
                    modèle.obtenirLivresParAuteursListString()
                }
            }
            val suggestionsAvecIcones = mutableListOf<String>()

            suggestionsAvecIcones.addAll(historique.map { "⏳ $it" })
            suggestionsAvecIcones.addAll(suggestions.map { "🔍 $it" })

            vue.mettreAJourSuggestions(suggestionsAvecIcones)
        }
    }

    fun lancerRecherche(rechercheText: String, critere: String) {
        if (rechercheText.isNotEmpty()) {
            job = CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    modèle.ajouterRecherche(rechercheText)
                    when (critere) {
                        "titre" -> modèle.obtenirLivresParNomTitre(rechercheText)
                        "auteur" -> modèle.obtenirLivresParNomAuteur(rechercheText)
                    }
                }
                vue.naviguer_resultat()
            }
        }
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