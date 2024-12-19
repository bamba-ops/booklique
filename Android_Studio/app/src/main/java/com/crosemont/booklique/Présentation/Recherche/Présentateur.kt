package com.crosemont.booklique.Présentation.Recherche

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Présentateur(private val vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle(vue.requireContext())

    fun traiter_historique_recherche() {
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
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
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
        } else {
            job = CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) { modèle.supprimerHistoriqueRecherche() }
                vue.mettreAJourSuggestions(emptyList())
            }
        }
    }

    fun traiter_mise_a_jour_suggestions(critère: String) {
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
        } else {
            job = CoroutineScope(Dispatchers.Main).launch {
                val historique =
                    withContext(Dispatchers.IO) { modèle.obtenirHistoriqueRecherches() }
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
    }

    fun lancerRecherche(rechercheText: String, critere: String) {
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
        } else {
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
}