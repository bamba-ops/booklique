package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso

class Vue_Resultat : Fragment() {

    lateinit var resultatRechercheConteneur: LinearLayout
    lateinit var textRechercheParDefaut: TextView
    private lateinit var présentateur: Présentateur

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_resultats_recherche, container, false)
    }

    //val livres = présentateur.traiter_obtenir_livre()

    fun afficherLivres(livre: Livre){
        val inflater = layoutInflater
        textRechercheParDefaut.text = "Aucun résultat trouvé."
        val livreView = inflater.inflate(
            R.layout.fragment_article_livre,
            resultatRechercheConteneur,
            false
        )



        val imageView = livreView.findViewById<ImageView>(R.id.livre_image)
        val titreTextView = livreView.findViewById<TextView>(R.id.livre_titre)
        val auteurTextView = livreView.findViewById<TextView>(R.id.livre_auteur)
        val genreTextView = livreView.findViewById<TextView>(R.id.livre_genre)
        val favoris = livreView.findViewById<ImageView>(R.id.icone_favoris)

        titreTextView.text = livre.titre
        auteurTextView.text = livre.auteur
        genreTextView.text = livre.genre
        if(présentateur.traiter_est_livre_favori(livre.isbn)){
            favoris.setImageResource(R.drawable.favoris_true)
        }

        Picasso.get()
            .load(livre.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageView)

        favoris.setOnClickListener {
            if(!présentateur.traiter_est_livre_favori(livre.isbn)){
                favoris.setImageResource(R.drawable.favoris_true)
                présentateur.traiter_ajouter_livre_favori(livre)
            } else {
                favoris.setImageResource(R.drawable.favoris_false)
                présentateur.traiter_retirer_livre_favori(livre.isbn)
            }
        }

        livreView.setOnClickListener {
//            // Ajout transfert data pour afficher details
//            val bundle = Bundle().apply {
//                putString("isbn", livre.isbn)
//                putString("titre", livre.titre)
//                putString("image_url", livre.image_url)
//                putString("description", livre.description)
//                putString("auteur", livre.auteur)
//                putString("editeur", livre.editeur)
//                putString("genre", livre.genre)
//                putString("date_publication", livre.date_publication.toString())
//                putInt("nombre_pages", livre.nombre_pages)
//                putString("disponibilite", if (livre.estDisponible()) "Disponible" else "Indisponible")
//            }
            présentateur.traiter_obtenir_livre(livre.isbn)
            findNavController().navigate(R.id.action_recherche_to_detail_livre)

        }

        resultatRechercheConteneur.addView(livreView)

    }




}