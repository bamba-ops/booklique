package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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

class Présentateur(private val vue: Vue, private val modèle: Modèle = Modèle()) {
    private var job: Job? = null

    fun traiter_mise_a_jour_suggestions(suggestions: String){
        job = CoroutineScope( Dispatchers.Main ).launch {
            if (suggestions == "titre") {
                vue.mettreAJourSuggestions(modèle.obtenirLivresParTitres())
            } else {
                vue.mettreAJourSuggestions(modèle.obtenirLivresParAuteursListString())
            }
        }
    }

    fun lancerRecherche(rechercheText: String, critere: String) {
        if(rechercheText.isNotEmpty()){
            when (critere) {
                "titre" -> modèle.obtenirLivresParNomTitre(rechercheText)
                "auteur" -> modèle.obtenirLivresParNomAuteur(rechercheText)
            }
            vue.naviguer_resultat()
        }
    }

}