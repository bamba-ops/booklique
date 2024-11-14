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
    private lateinit var livresFavoris: List<Livre>
    private lateinit var présentateur: Présentateur

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
        livresFavoris = Data.obtenirLivresFavoris()
        présentateur = Présentateur(this)

        afficherLivresFavoris()

    }

    fun afficherLivresFavoris(){
        for(livreFavori in livresFavoris){
            val inflater = layoutInflater
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

            titreTextView.text = livreFavori.titre
            auteurTextView.text = livreFavori.auteur
            genreTextView.text = livreFavori.genre
            iconeFavoris.setImageResource(R.drawable.favoris_true)

            Picasso.get()
                .load(livreFavori.image_url)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView)

            livresFavorisView.setOnClickListener {
                // Ajout transfert data pour afficher details
                val bundle = Bundle().apply {
                    putString("isbn", livreFavori.isbn)
                    putString("titre", livreFavori.titre)
                    putString("image_url", livreFavori.image_url)
                    putString("description", livreFavori.description)
                    putString("auteur", livreFavori.auteur)
                    putString("editeur", livreFavori.editeur)
                    putString("genre", livreFavori.genre)
                    putString("date_publication", livreFavori.date_publication.toString())
                    putInt("nombre_pages", livreFavori.nombre_pages)
                    putString("disponibilite", if (livreFavori.estDisponible()) "Disponible" else "Indisponible")
                }
                findNavController().navigate(R.id.action_favoris_to_detail_livre,bundle)

            }

            iconeFavoris.setOnClickListener {
                iconeFavoris.setImageResource(R.drawable.favoris_false)
                Data.retirerLivreFavoriParISBN(livreFavori.isbn)
                resultatLivresFavoris.removeView(livresFavorisView)
            }

            resultatLivresFavoris.addView(livresFavorisView)
        }

    }
}