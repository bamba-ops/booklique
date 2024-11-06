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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accueil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionGenres: LinearLayout = view.findViewById(R.id.section_genres)
        val titreLibrairie: TextView = view.findViewById(R.id.titre_librairie)
        val texteParcourirGenres: TextView = view.findViewById(R.id.texte_parcourir_genres)
        val texteNouveautes: TextView = view.findViewById(R.id.texte_nouveautes)
        val nomAuteur: TextView = view.findViewById(R.id.nom_auteur)
        val nomAuteur2: TextView = view.findViewById(R.id.nom_auteur_2)
        val nomAuteur3: TextView = view.findViewById(R.id.nom_auteur_3)
        val sectionAuteur: LinearLayout = view.findViewById(R.id.carte_auteur)
        val sectionAuteur2: LinearLayout = view.findViewById(R.id.carte_auteur_2)
        val sectionAuteur3: LinearLayout = view.findViewById(R.id.carte_auteur_3)
        val sectionNouveautes: LinearLayout = view.findViewById(R.id.section_nouveautes)

        val icMenuGenres: ImageView = view.findViewById(R.id.ic_menu_genres)
        val icFlecheGenres: ImageView = view.findViewById(R.id.ic_fleche_genres)
        val icFlecheNouveautes: ImageView = view.findViewById(R.id.ic_fleche_nouveautes)

        val imageAuteur: ImageView = view.findViewById(R.id.image_auteur)
        val imageAuteur2: ImageView = view.findViewById(R.id.image_auteur_2)
        val imageAuteur3: ImageView = view.findViewById(R.id.image_auteur_3)
        val imageNouveaute1: ImageView = view.findViewById(R.id.image_nouveaute_1)
        val imageNouveaute2: ImageView = view.findViewById(R.id.image_nouveaute_2)
        val imageNouveaute3: ImageView = view.findViewById(R.id.image_nouveaute_3)
        val imageNouveaute4: ImageView = view.findViewById(R.id.image_nouveaute_4)

        // Récupération des livres depuis data
        val livres = Data.obtenirLivresDemo()

        titreLibrairie.text = "Librairie"
        texteParcourirGenres.text = "Parcourir les genres"
        texteNouveautes.text = "Nouveautés"

        // Associer chaque livre aux images et au texte
        if (livres.size >= 4) {
            afficherLivre(livres[0], imageNouveaute1, view.findViewById(R.id.nom_auteur))
            afficherLivre(livres[1], imageNouveaute2, view.findViewById(R.id.nom_auteur_2))
            afficherLivre(livres[2], imageNouveaute3, view.findViewById(R.id.nom_auteur_3))
            afficherLivre(livres[3], imageNouveaute4, null)
        }



        if(livres.size >= 3){
            afficherLivreParAuteur(livres[2], imageAuteur, nomAuteur)
            afficherLivreParAuteur(livres[1], imageAuteur2, nomAuteur2)
            afficherLivreParAuteur(livres[3], imageAuteur3, nomAuteur3)
        }


        sectionGenres.setOnClickListener {
            findNavController().navigate(R.id.action_accueil_to_genres)
        }

        sectionAuteur.setOnClickListener{
            findNavController().navigate(R.id.action_accueil_to_recherche)
        }

        sectionAuteur2.setOnClickListener{
            findNavController().navigate(R.id.action_accueil_to_recherche)
        }

        sectionAuteur3.setOnClickListener{
            findNavController().navigate(R.id.action_accueil_to_recherche)
        }

        sectionNouveautes.setOnClickListener {
            findNavController().navigate(R.id.action_accueil_to_recherche)
        }

        imageNouveaute1.setOnClickListener {
            val livreSelectionne = livres[0]
            val action = Vue.newInstance(livreSelectionne)
            findNavController().navigate(R.id.action_accueil_to_reservation, action.arguments)
        }


        imageNouveaute2.setOnClickListener{
            val livreSelectionne = livres[1]
            val action = Vue.newInstance(livreSelectionne)
            findNavController().navigate(R.id.action_accueil_to_reservation, action.arguments)
        }

        imageNouveaute3.setOnClickListener {
            val livreSelectionne = livres[2]
            val action = Vue.newInstance(livreSelectionne)
            findNavController().navigate(R.id.action_accueil_to_reservation, action.arguments)
        }

        imageNouveaute4.setOnClickListener {
            val livreSelectionne = livres[3]
            val action = Vue.newInstance(livreSelectionne)
            findNavController().navigate(R.id.action_accueil_to_reservation, action.arguments)
        }
    }

    // Fonction pour afficher un livre dans une image et mettre à jour le nom de l'auteur
    private fun afficherLivre(livre: Livre, imageView: ImageView, auteurTextView: TextView?) {
        Picasso.get()
            .load(livre.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageView)
        auteurTextView?.text = livre.auteur
        Picasso.get().setIndicatorsEnabled(true)
        Picasso.get().isLoggingEnabled = true
    }

    private fun afficherLivreParAuteur(livre: Livre, imageView: ImageView, auteurTextView: TextView?) {
        Picasso.get()
            .load(livre.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageView)
        auteurTextView?.text = livre.auteur
    }

    companion object {
        private const val ARG_LIVRE = "livre"

        fun newInstance(livre: Livre): Vue {
            val fragment = Vue()
            val args = Bundle().apply {
                putParcelable(ARG_LIVRE, livre)
            }
            fragment.arguments = args
            return fragment
        }
    }


}