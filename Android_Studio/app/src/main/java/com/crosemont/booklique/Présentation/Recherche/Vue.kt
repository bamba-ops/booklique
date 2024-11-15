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
import android.widget.ProgressBar
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
    private lateinit var chargement: ProgressBar

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
        chargement = view.findViewById(R.id.chargement)
        présentateur = Présentateur(this)

        présentateur.traiter_livres_par_nouveautes()
        présentateur.traiter_livres_par_auteur()

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
    }



    @SuppressLint("SetTextI18n")
    fun préparationAfficherRésultatsRecherche() {
        afficherTextParDefaut(true)
        supprimerResultatRechercheConteneur()
    }

    fun afficherChargement(isCharger: Boolean){
        if(isCharger){
            chargement.visibility = View.VISIBLE
        } else {
            chargement.visibility = View.GONE
        }
    }

    fun modifierTextRechercheUtilisateur(text: String){
        txtRechercheUtilisateur.text = text
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

    fun afficherTextRechercheUtilisateur(isVisible: Boolean){
        if(isVisible && txtRechercheUtilisateur.visibility == View.GONE){
            txtRechercheUtilisateur.visibility = View.VISIBLE
        } else if(!isVisible && txtRechercheUtilisateur.visibility == View.VISIBLE) {
            txtRechercheUtilisateur.visibility = View.GONE
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

    fun modifierTxtCritère(critère: String){
            txtRechercheUtilisateur.text = "Recherche : $critère"
    }

    fun préparationAfficherLivres(critère: String){
        afficherTextParDefaut(false)
        afficherDefilementResultatRecherche(true)
        modifierTxtCritère(critère)
        afficherTextRechercheUtilisateur(true)
    }

    fun afficherLivres(livre: Livre){
        val inflater = layoutInflater
        val livreView = inflater.inflate(
            R.layout.fragment_article_livre,
            resultatRechercheConteneur,
            false
        )



        val imageView = livreView.findViewById<ImageView>(R.id.livre_image)
        val titreTextView = livreView.findViewById<TextView>(R.id.livre_titre)
        val auteurTextView = livreView.findViewById<TextView>(R.id.livre_auteur)
        val genreTextView = livreView.findViewById<TextView>(R.id.livre_genre)

        titreTextView.text = livre.titre
        auteurTextView.text = livre.auteur
        genreTextView.text = livre.genre

        Picasso.get()
            .load(livre.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageView)

        livreView.setOnClickListener {
            // Ajout transfert data pour afficher details
            val bundle = Bundle().apply {
                putString("isbn", livre.isbn)
                putString("titre", livre.titre)
                putString("image_url", livre.image_url)
                putString("description", livre.description)
                putString("auteur", livre.auteur)
                putString("editeur", livre.editeur)
                putString("genre", livre.genre)
                putString("date_publication", livre.date_publication.toString())
                putInt("nombre_pages", livre.nombre_pages)
                putString("disponibilite", if (livre.estDisponible()) "Disponible" else "Indisponible")
            }
            findNavController().navigate(R.id.action_recherche_to_detail_livre,bundle)

        }

        resultatRechercheConteneur.addView(livreView)

    }






}

