package com.crosemont.booklique.Présentation.Favoris

import Livre
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.mork_data.Data
import com.squareup.picasso.Picasso


class Vue : Fragment() {

    private lateinit var resultatLivresFavoris: LinearLayout
    private lateinit var présentateur: Présentateur
    private lateinit var  textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoris, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultatLivresFavoris = view.findViewById(R.id.resultat_livres_favoris)
        textView = view.findViewById(R.id.rienFavoris)
        présentateur = Présentateur(this)
        présentateur.chargerLivresFavoris()


    }

    fun afficherLivresFavoris(livres: List<Livre>) {
        if (livres.isEmpty()) {
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }

        resultatLivresFavoris.removeAllViews()
        val inflater = layoutInflater

        for (livre in livres) {
            val livresFavorisView = inflater.inflate(
                R.layout.fragment_article_livre,
                resultatLivresFavoris,
                false
            )

            val imageView = livresFavorisView.findViewById<ImageView>(R.id.livre_image)
            val titreTextView = livresFavorisView.findViewById<TextView>(R.id.livre_titre)
            val auteurTextView = livresFavorisView.findViewById<TextView>(R.id.livre_auteur)
            val genreTextView = livresFavorisView.findViewById<TextView>(R.id.livre_genre)
            val iconeFavoris = livresFavorisView.findViewById<ImageView>(R.id.icone_favoris)

            titreTextView.text = livre.titre
            auteurTextView.text = livre.auteur
            genreTextView.text = livre.genre
            iconeFavoris.setImageResource(R.drawable.favoris_true)

            Picasso.get()
                .load(livre.image_url)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView)

            livresFavorisView.setOnClickListener {
                présentateur.ouvrirDétailsLivre(livre.isbn)
                findNavController().navigate(R.id.action_favoris_to_detail_livre)
            }

            iconeFavoris.setOnClickListener {
                présentateur.ajouterLivresFavoris(livre)
            }

            iconeFavoris.setOnClickListener {
                présentateur.retirerDesFavoris(livre)
            }

            resultatLivresFavoris.addView(livresFavorisView)
        }
    }
}