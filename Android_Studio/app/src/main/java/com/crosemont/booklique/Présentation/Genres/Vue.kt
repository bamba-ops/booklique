package com.crosemont.booklique.Présentation.Genres

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R

class Vue : Fragment() {

    private lateinit var genreAffaires: LinearLayout
    private lateinit var genreBiographies: LinearLayout
    private lateinit var genreDeveloppementPersonnel: LinearLayout
    private lateinit var genreFiction: LinearLayout
    private lateinit var genreHistoire: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genreAffaires = view.findViewById(R.id.genre_affaires)
        genreBiographies = view.findViewById(R.id.genre_biographies)
        genreDeveloppementPersonnel = view.findViewById(R.id.genre_developpement_personnel)
        genreFiction = view.findViewById(R.id.genre_fiction)
        genreHistoire = view.findViewById(R.id.genre_histoire)

        genreAffaires.setOnClickListener { navigateToRecherche("Afffaires") }
        genreBiographies.setOnClickListener { navigateToRecherche("Biographies") }
        genreDeveloppementPersonnel.setOnClickListener { navigateToRecherche("Développement personnel") }
        genreFiction.setOnClickListener { navigateToRecherche("Fiction et littérature") }
        genreHistoire.setOnClickListener { navigateToRecherche("Histoire") }
    }

    private fun navigateToRecherche(genre: String) {
        val bundle = Bundle().apply {
            putString("genre", genre)
            putBoolean("obtenirLivreParGenre", true)
        }
        findNavController().navigate(R.id.action_genres_to_recherche, bundle)
    }
}
