package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Présentateur(private val vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()

    fun afficherLivresParGenre(genre: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val livres = modèle.obtenirLivresParGenre(genre)
            vue.afficherRésultatsRecherche(livres, genre)
        }
    }

    fun afficherLivresParAuteur(auteur: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val livres = modèle.obtenirLivresParAuteur(auteur)
            vue.afficherRésultatsRecherche(livres,auteur)
        }
    }

    fun afficherLivresParTitre(titre: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val livres = modèle.obtenirLivresParTitre(titre)
            vue.afficherRésultatsRecherche(livres, titre)
        }
    }


    fun afficherLivresParNouveaute() {
        job = CoroutineScope(Dispatchers.Main).launch {
            val livres = modèle.trierLivresParNouveaute()
            vue.afficherRésultatsRecherche(livres,"")
        }
    }

    fun lancerRecherche(rechercheTexte: String) {
        val texte = rechercheTexte.trim()
        if (texte.isNotEmpty()) {
            when {
                texte.startsWith("auteur:", ignoreCase = true) -> {
                    val auteurRecherche = texte.removePrefix("auteur:").trim()
                    afficherLivresParAuteur(auteurRecherche)
                }
                texte.startsWith("titre:", ignoreCase = true) -> {
                    val titreRecherche = texte.removePrefix("titre:").trim()
                    afficherLivresParTitre(titreRecherche)
                }
                texte.startsWith("genre:", ignoreCase = true) -> {
                    val genreRecherche = texte.removePrefix("genre:").trim()
                    afficherLivresParGenre(genreRecherche)
                }
                else -> {
                    afficherLivresParTitre(texte)
                }
            }
        }
    }

    fun afficherLivres(livre: Livre) {
        val livreView = vue.layoutInflater.inflate(
            R.layout.fragment_article_livre,
            vue.resultatRechercheConteneur,
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
            vue.findNavController().navigate(R.id.action_recherche_to_detail_livre, bundle)
        }

        vue.resultatRechercheConteneur.addView(livreView)
    }
}
