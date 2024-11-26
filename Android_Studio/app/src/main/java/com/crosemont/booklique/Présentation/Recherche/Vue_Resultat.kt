package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso

class Vue_Resultat : Fragment() {

    lateinit var resultatRechercheConteneur: LinearLayout
    lateinit var textRechercheParDefaut: TextView
    private lateinit var présentateur: Présentateur_Resultat
    private lateinit var affichageDefilementResultatRecherche: ScrollView
    private lateinit var chargement: ProgressBar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_resultats_recherche, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultatRechercheConteneur = view.findViewById(R.id.resultat_de_recherche_conteneur)
        affichageDefilementResultatRecherche = view.findViewById(R.id.affichage_defilement_resultat_de_recherche)
        textRechercheParDefaut = view.findViewById(R.id.text_recherche_par_defaut)
        chargement = view.findViewById(R.id.chargement)
        présentateur = Présentateur_Resultat(this)

        présentateur.traiter_livres_par_nouveautes()
        présentateur.traiter_livres_par_auteur()
        présentateur.traiter_livres_par_genre()
        présentateur.traiter_livre_par_titre()

    }

    fun afficherLivres(livre: Livre){
        val inflater = layoutInflater
        textRechercheParDefaut.text = "Aucun résultat trouvé."
        val livreView = inflater.inflate(
            R.layout.fragment_article_livre,
            resultatRechercheConteneur,
            false
        )

        val imageView = livreView.findViewById<ImageView>(R.id.livre_image)
        val titreTextView = livreView.findViewById<TextView>(R.id.livre_titre)
        val auteurTextView = livreView.findViewById<TextView>(R.id.livre_auteur)
        val genreTextView = livreView.findViewById<TextView>(R.id.livre_genre)
        val favoris = livreView.findViewById<ImageView>(R.id.icone_favoris)

        titreTextView.text = livre.titre
        auteurTextView.text = livre.auteur
        genreTextView.text = livre.genre
        if(présentateur.traiter_est_livre_favori(livre.isbn)){
            favoris.setImageResource(R.drawable.favoris_true)
        }

        Picasso.get()
            .load(livre.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageView)

        favoris.setOnClickListener {
            if(!présentateur.traiter_est_livre_favori(livre.isbn)){
                favoris.setImageResource(R.drawable.favoris_true)
                présentateur.traiter_ajouter_livre_favori(livre)
            } else {
                favoris.setImageResource(R.drawable.favoris_false)
                présentateur.traiter_retirer_livre_favori(livre.isbn)
            }
        }

        livreView.setOnClickListener {
            présentateur.traiter_obtenir_livre(livre.isbn)
            findNavController().navigate(R.id.action_resultat_to_detail_livre)

        }

        resultatRechercheConteneur.addView(livreView)

    }

    fun afficherChargement(isCharger: Boolean){
        if(isCharger){
            chargement.visibility = View.VISIBLE
        } else {
            chargement.visibility = View.GONE
        }
    }

    fun supprimerResultatRechercheConteneur(){
        resultatRechercheConteneur.removeAllViews()
    }

    fun afficherTextParDefaut(isVisible: Boolean){
        if(isVisible && textRechercheParDefaut.visibility == View.GONE){
            textRechercheParDefaut.visibility = View.VISIBLE
        } else if(!isVisible && textRechercheParDefaut.visibility == View.VISIBLE) {
            textRechercheParDefaut.visibility = View.GONE
        }
    }

    fun afficherDefilementResultatRecherche(isVisible: Boolean){
        if(isVisible && affichageDefilementResultatRecherche.visibility == View.GONE){
            affichageDefilementResultatRecherche.visibility = View.VISIBLE
        } else if(!isVisible && affichageDefilementResultatRecherche.visibility == View.VISIBLE) {
            affichageDefilementResultatRecherche.visibility = View.GONE
        }
    }

    fun modifierTextRechercheParDefaut(text: String){
        textRechercheParDefaut.text = text
    }


    fun préparationAfficherLivres(critère: String){
        afficherTextParDefaut(false)
        afficherDefilementResultatRecherche(true)
    }


}