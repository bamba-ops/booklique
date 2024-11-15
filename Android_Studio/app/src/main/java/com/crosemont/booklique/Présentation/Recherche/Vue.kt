package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.mork_data.Data
import com.squareup.picasso.Picasso
import java.util.Date

class Vue : Fragment() {

    private lateinit var entreeRecherche: AutoCompleteTextView
    lateinit var resultatRechercheConteneur: LinearLayout
    private lateinit var affichageDefilementResultatRecherche: ScrollView
    private lateinit var textRechercheParDefaut: TextView
    private lateinit var txtRechercheUtilisateur: TextView
    private lateinit var btnRecherce : ImageButton
    private lateinit var présentateur: Présentateur

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recherche, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        entreeRecherche = view.findViewById(R.id.entree_recherche)
        resultatRechercheConteneur =
            view.findViewById(R.id.resultat_de_recherche_conteneur)
        affichageDefilementResultatRecherche =
            view.findViewById(R.id.affichage_defilement_resultat_de_recherche)
        textRechercheParDefaut = view.findViewById(R.id.text_recherche_par_defaut)
        txtRechercheUtilisateur = view.findViewById(R.id.texte_recherche_utilisateur)
        btnRecherce = view.findViewById(R.id.btnRecherche)
        présentateur = Présentateur(this)


        entreeRecherche.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                val rechercheTexte = entreeRecherche.text.toString()
                présentateur.lancerRecherche(rechercheTexte)
                true
            } else {
                false
            }
        }

        // Action pour le bouton de recherche
        btnRecherce.setOnClickListener {
            val rechercheTexte = entreeRecherche.text.toString()
            présentateur.lancerRecherche(rechercheTexte)
        }


        arguments?.let { bundle ->
            when {
                bundle.containsKey("obtenirLivreParGenre") -> {
                    val genre = bundle.getString("genre")
                    if (genre != null) présentateur.afficherLivresParGenre(genre)
                }

                bundle.containsKey("obtenirLivreParTitre") -> {
                    val Titre = bundle.getString("titre")
                    if (Titre != null) présentateur.afficherLivresParTitre(Titre)
                }

                bundle.containsKey("obtenirLivreParAuteur") -> {
                    val nomAuteur = bundle.getString("auteur")
                    if (nomAuteur != null) présentateur.afficherLivresParAuteur(nomAuteur)
                }

                bundle.getBoolean("trierParNouveaute", false) -> {
                    présentateur.afficherLivresParNouveaute()
                }
            }
        }
    }



    @SuppressLint("SetTextI18n")
    fun afficherRésultatsRecherche(livres: List<Livre>, rechercheTexte: String) {
        textRechercheParDefaut.visibility = View.VISIBLE
        resultatRechercheConteneur.removeAllViews()
        txtRechercheUtilisateur.text = rechercheTexte

        if (livres.isEmpty()) {
            affichageDefilementResultatRecherche.visibility = View.GONE
            textRechercheParDefaut.text = "Aucun résultat trouvé pour '$rechercheTexte'."
        } else {
            textRechercheParDefaut.text = "Recherche : $rechercheTexte"
            affichageDefilementResultatRecherche.visibility = View.VISIBLE

            for (livre in livres) {
                présentateur.afficherLivres(livre)
            }
        }
    }



}

