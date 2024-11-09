package com.crosemont.booklique.Présentation.Accueil

import Livre
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso
import com.crosemont.booklique.domaine.mork_data.Data

class Vue : Fragment() {

    private lateinit var sectionGenres: LinearLayout
    private lateinit var sectionNouveautes: LinearLayout
    private lateinit var carteAuteur: LinearLayout
    private lateinit var listeNouveautes: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accueil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sectionGenres = view.findViewById(R.id.section_genres)
        sectionNouveautes = view.findViewById(R.id.section_nouveautes)
        listeNouveautes = view.findViewById(R.id.liste_nouveautes)
        carteAuteur = view.findViewById(R.id.carte_auteurs)

        // Récupération des livres depuis data
        val livres = Data.obtenirLivresDemo()

        afficherCartesAuteurs(livres)
        afficherListeNouveautes(livres)

        sectionGenres.setOnClickListener {
            findNavController().navigate(R.id.action_accueil_to_genres)
        }

        sectionNouveautes.setOnClickListener {
            val bundle = Bundle().apply {
                putBoolean("trierParNouveaute", true)
            }
            findNavController().navigate(R.id.action_accueil_to_recherche, bundle)
        }


    }

    fun afficherListeNouveautes(livres: List<Livre>) {
        for(livre in livres){
            val listeNouveautesView = LayoutInflater.from(context).inflate(R.layout.fragment_section_nouveautes, listeNouveautes, false)

            val imageNouveaute: ImageView = listeNouveautesView.findViewById(R.id.image_nouveaute)

            Picasso.get()
                .load(livre.image_url)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageNouveaute)

            imageNouveaute.setOnClickListener {
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
                findNavController().navigate(R.id.action_accueil_to_detail_livre, bundle)
            }


            listeNouveautes.addView(listeNouveautesView)

        }
    }

    fun afficherCartesAuteurs(livres: List<Livre>){
        for(livre in livres){
            val carteAuteurView = LayoutInflater.from(context).inflate(R.layout.fragment_carte_auteur, carteAuteur, false)

            val nomAuteur: TextView = carteAuteurView.findViewById(R.id.nom_auteur)
            val imageAuteur: ImageView = carteAuteurView.findViewById(R.id.image_auteur)
            val btnCarteAuteur: LinearLayout = carteAuteurView.findViewById(R.id.carte_auteur)

            nomAuteur.text = livre.auteur
            Picasso.get()
                .load(livre.image_url)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageAuteur)

            btnCarteAuteur.setOnClickListener{
                val bundle = Bundle()
                bundle.putString("auteur", livre.auteur)
                bundle.putBoolean("obtenirLivreParAuteur", true)

                findNavController().navigate(R.id.action_accueil_to_recherche, bundle)
            }

            carteAuteur.addView(carteAuteurView)

        }
    }



}