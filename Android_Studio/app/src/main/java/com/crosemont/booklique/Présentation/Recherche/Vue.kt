package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso
import java.util.Date

class Vue : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recherche, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genre = arguments?.getString("genre") ?: "" // Retrieve the genre if passed
        val entreeRecherche: AutoCompleteTextView = view.findViewById(R.id.entree_recherche)
        val resultatRechercheConteneur: LinearLayout =
            view.findViewById(R.id.resultat_de_recherche_conteneur)
        val affichageDefilementResultatRecherche: ScrollView =
            view.findViewById(R.id.affichage_defilement_resultat_de_recherche)
        val textRechercheParDefaut: TextView = view.findViewById(R.id.text_recherche_par_defaut)

        // Adapter for the search suggestions
        val suggestions = arrayOf("Story of two friends", "Soul")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestions)
        entreeRecherche.setAdapter(adapter)

        // Set a listener for search queries
        entreeRecherche.setOnItemClickListener { _, _, position, _ ->
            val choix = adapter.getItem(position) ?: ""
            resultatRechercheConteneur.removeAllViews()
            val resultat = obtenirResultatRecherche(choix, genre)

            updateUI(resultat, resultatRechercheConteneur, affichageDefilementResultatRecherche, textRechercheParDefaut)
        }


        if (genre.isNotEmpty()) {
            val resultat = obtenirResultatRecherche("", genre)  // Empty query, filter by genre
            updateUI(resultat, resultatRechercheConteneur, affichageDefilementResultatRecherche, textRechercheParDefaut)
        }
    }

    private fun updateUI(
        resultat: List<Livre>,
        resultatRechercheConteneur: LinearLayout,
        affichageDefilementResultatRecherche: ScrollView,
        textRechercheParDefaut: TextView
    ) {
        if (resultat.isEmpty()) {
            textRechercheParDefaut.visibility = View.VISIBLE
            affichageDefilementResultatRecherche.visibility = View.GONE
        } else {
            textRechercheParDefaut.visibility = View.GONE
            affichageDefilementResultatRecherche.visibility = View.VISIBLE

            // Iterate over the result and display the books
            for (livre in resultat) {
                val inflater = layoutInflater
                val itemView = inflater.inflate(
                    R.layout.fragment_article_livre,
                    resultatRechercheConteneur,
                    false
                )

                val imageView = itemView.findViewById<ImageView>(R.id.livre_image)
                val titreTextView = itemView.findViewById<TextView>(R.id.livre_titre)
                val auteurTextView = itemView.findViewById<TextView>(R.id.livre_auteur)
                val editeurTextView = itemView.findViewById<TextView>(R.id.livre_editeur)
                val genreTextView = itemView.findViewById<TextView>(R.id.livre_genre)
                val descriptionTextView =
                    itemView.findViewById<TextView>(R.id.livre_description)

                titreTextView.text = livre.titre
                auteurTextView.text = livre.auteur
                editeurTextView.text = livre.editeur
                genreTextView.text = livre.genre
                descriptionTextView.text = livre.description

                Picasso.get()
                    .load(livre.image_url)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(imageView)

                itemView.setOnClickListener {
                    findNavController().navigate(R.id.action_recherche_to_detail_livre)
                }

                resultatRechercheConteneur.addView(itemView)
            }
        }
    }


    fun obtenirResultatRecherche(query: String, genreFilter: String): List<Livre> {
        val books = listOf(
            Livre(
                "12345",
                "https://i.imghippo.com/files/YFa6767kO.png",
                "Story of two friends",
                "Dans une petite ville entourée de forêts denses et de montagnes, Alex et Jamie partagent une amitié aussi rare que précieuse.",
                "Avery Davis",
                "Éditions Horizons Littéraires",
                "Histoire",
                Date(),
                300,
                10
            ),
            Livre(
                "67890",
                "https://i.imghippo.com/files/Al2125oNk.png",
                "Soul",
                "À travers un voyage intime et spirituel, Soul explore les profondeurs de l'âme humaine, les questions d'identité et le besoin de se reconnecter avec soi-même.",
                "Olivia Wilson",
                "Éditions Éclipse",
                "Histoire",
                Date(),
                250,
                5
            ),


        )

        return books.filter { livre ->
            (genreFilter.isEmpty() || livre.genre.equals(genreFilter, ignoreCase = true)) &&
                    (query.isEmpty() || livre.titre.contains(query, ignoreCase = true))
        }
    }
}
