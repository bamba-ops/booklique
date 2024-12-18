package com.crosemont.booklique.Présentation.Recherche

import android.content.Context
import androidx.appcompat.app.AlertDialog
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
                vue.mettre_a_jour_suggestion(suggestionsAvecIcones)
            }
        }
    }

    fun traiter_supprimer_recherche_historique() {
        if(!modèle.connexion(vue.requireContext())){
            traiterConnexion(vue.requireContext())
        } else {
            job = CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) { modèle.supprimerHistoriqueRecherche() }
                vue.mettre_a_jour_suggestion(emptyList())
            }
        }
    }

    fun traiter_mise_a_jour_suggestions(checkedId: Int) {
        if(!modèle.connexion(vue.requireContext())){
            traiterConnexion(vue.requireContext())
        } else {
            job = CoroutineScope(Dispatchers.Main).launch {
                val historique =
                    withContext(Dispatchers.IO) { modèle.obtenirHistoriqueRecherches() }
                val suggestions = withContext(Dispatchers.IO) {
                    val critère = if ( checkedId == vue.charger_radio_id()) "titre" else "auteur"
                    if (critère == "titre") {
                        modèle.obtenirLivresParTitres()
                    } else {
                        modèle.obtenirLivresParAuteursListString()
                    }
                }
                val suggestionsAvecIcones = mutableListOf<String>()

                suggestionsAvecIcones.addAll(historique.map { "⏳ $it" })
                suggestionsAvecIcones.addAll(suggestions.map { "🔍 $it" })

                vue.mettre_a_jour_suggestion(suggestionsAvecIcones)
            }
        }
    }

    fun traiter_lancer_recherche(position: Int) {
        if(!modèle.connexion(vue.requireContext())){
            traiterConnexion(vue.requireContext())
        } else {

            val suggestion = vue.charger_barre_recherche(position)
            val rechercheText = suggestion.removePrefix("🔍 ").removePrefix("⏳ ")
            vue.charger_text_barre_recherche(rechercheText)
            val critere = if (vue.charger_group_radio_checked_id() == vue.charger_radio_id()) "titre" else "auteur"

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
    }

    fun traiter_boutton_recherche(){
        if(!modèle.connexion(vue.requireContext())){
            traiterConnexion(vue.requireContext())
        } else {

            val rechercheText = vue.avoir_text_barre_recherche().trim()
            val critere = if (vue.charger_group_radio_checked_id() == vue.charger_radio_id()) "titre" else "auteur"

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