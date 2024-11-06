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

        genreAffaires.setOnClickListener {
            findNavController().navigate(R.id.action_genres_to_recherche)
        }
    }
}