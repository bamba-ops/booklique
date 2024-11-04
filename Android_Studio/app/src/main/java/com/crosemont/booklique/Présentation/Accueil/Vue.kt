package com.crosemont.booklique.Présentation.Accueil

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

        titreLibrairie.text = "Librairie"
        texteParcourirGenres.text = "Parcourir les genres"
        nomAuteur.text = "Olivia Wilson"
        texteNouveautes.text = "Nouveautés"
        nomAuteur2.text = "Lorna Alvarado"
        nomAuteur3.text = "Avery Davis"

        val auteurImageUrl = "https://i.imghippo.com/files/Al2125oNk.png"
        val auteurImageUrl2 = "https://i.imghippo.com/files/FJtq5630MY.png"
        val auteurImageUrl3 = "https://i.imghippo.com/files/YFa6767kO.png"
        val nouveauteImageUrl1 = "https://i.imghippo.com/files/XF7523fok.png"
        val nouveauteImageUrl2 = "https://i.imghippo.com/files/AP7294k.png"
        val nouveauteImageUrl3 = "https://i.imghippo.com/files/Xg9432qo.png"
        val nouveauteImageUrl4 = "https://i.imghippo.com/files/Uvff8490Ak.png"


        Picasso.get()
            .load(auteurImageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageAuteur)

        Picasso.get()
            .load(auteurImageUrl2)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageAuteur2)

        Picasso.get()
            .load(auteurImageUrl3)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageAuteur3)

        Picasso.get()
            .load(nouveauteImageUrl1)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageNouveaute1)

        Picasso.get()
            .load(nouveauteImageUrl2)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageNouveaute2)

        Picasso.get()
            .load(nouveauteImageUrl3)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageNouveaute3)

        Picasso.get()
            .load(nouveauteImageUrl4)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageNouveaute4)

        Picasso.get().setIndicatorsEnabled(true)
        Picasso.get().isLoggingEnabled = true

        sectionGenres.setOnClickListener {
            findNavController().navigate(R.id.accueil)
        }

        sectionAuteur.setOnClickListener{
            findNavController().navigate(R.id.accueil)
        }

        sectionAuteur2.setOnClickListener{
            findNavController().navigate(R.id.accueil)
        }

        sectionAuteur3.setOnClickListener{
            findNavController().navigate(R.id.accueil)
        }

        sectionNouveautes.setOnClickListener {
            findNavController().navigate(R.id.accueil)
        }

        imageNouveaute1.setOnClickListener{
            findNavController().navigate(R.id.accueil)
        }

        imageNouveaute2.setOnClickListener{
            findNavController().navigate(R.id.accueil)
        }

        imageNouveaute3.setOnClickListener {
            findNavController().navigate(R.id.accueil)
        }

        imageNouveaute4.setOnClickListener {
            findNavController().navigate(R.id.accueil)
        }
    }

}