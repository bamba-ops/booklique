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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genreAffaires: LinearLayout = view.findViewById(R.id.genre_affaires)
        val genreBiographies: LinearLayout = view.findViewById(R.id.genre_biographies)
        val genreDeveloppementPersonnel: LinearLayout = view.findViewById(R.id.genre_developpement_personnel)
        val genreFiction: LinearLayout = view.findViewById(R.id.genre_fiction)
        val genreHistoire: LinearLayout = view.findViewById(R.id.genre_histoire)

        // Set click listeners for each genre
        genreAffaires.setOnClickListener { navigateToRecherche("Affaires") }
        genreBiographies.setOnClickListener { navigateToRecherche("Biographies et mémoires") }
        genreDeveloppementPersonnel.setOnClickListener { navigateToRecherche("Développement personnel") }
        genreFiction.setOnClickListener { navigateToRecherche("Fiction et littérature") }
        genreHistoire.setOnClickListener { navigateToRecherche("Histoire") }
    }

    // Navigate to RechercheFragment with the selected genre as an argument
    private fun navigateToRecherche(genre: String) {
        val bundle = Bundle().apply {
            putString("genre", genre)
        }
        findNavController().navigate(R.id.action_genres_to_rechercheFragment, bundle)
    }
}
