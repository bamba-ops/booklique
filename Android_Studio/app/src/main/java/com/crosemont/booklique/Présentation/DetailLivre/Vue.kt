package com.crosemont.booklique.Présentation.DetailLivre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.mork_data.Data

class Vue : Fragment() {

    private lateinit var imageLivre: ImageView
    private lateinit var titreLivre: TextView
    private lateinit var statutLivre: TextView
    private lateinit var descriptionCourte: TextView
    private lateinit var descriptionComplete: TextView
    private lateinit var auteurLivre: TextView
    private lateinit var echeanceLivre: TextView
    private lateinit var buttonReservation: Button
    private lateinit var buttonFavoris: ImageButton
    private lateinit var isbn: String
    var isFavoris: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_livre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageLivre = view.findViewById(R.id.image_livre_details)
        titreLivre = view.findViewById(R.id.titre_livre_details)
        statutLivre = view.findViewById(R.id.statut_livre)
        descriptionCourte = view.findViewById(R.id.description_court_details)
        descriptionComplete = view.findViewById(R.id.description_complet_livre_detail)
        auteurLivre = view.findViewById(R.id.auteur_livre_details)
        echeanceLivre = view.findViewById(R.id.echeance_livre)
        buttonReservation = view.findViewById(R.id.bouton_reserver_details)
        buttonFavoris = view.findViewById(R.id.bouton_favoris_details)

        // Récupérer les données passées depuis Accueil
        arguments?.let { bundle ->
            isbn = bundle.getString("isbn").toString()
            titreLivre.text = bundle.getString("titre")
            statutLivre.text = bundle.getString("disponibilite")
            descriptionCourte.text = bundle.getString("description")?.take(25)?.plus("...") ?: "Description non disponible"
            descriptionComplete.text = bundle.getString("description")
            auteurLivre.text = bundle.getString("auteur")
            echeanceLivre.text = bundle.getString("date_publication")

            // Charger l'image avec Glide
            val imageUrl = bundle.getString("image_url")
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageLivre)

            // vérification des bouttons de reservation
            if (bundle.getString("disponibilite") == "Disponible")
                buttonReservation.isEnabled = true
            else
                buttonReservation.isEnabled = false

            if (Data.estLivreFavori(isbn)) {
                isFavoris = true
                buttonFavoris.setImageResource(R.drawable.favoris_true)
            }

            buttonFavoris.setOnClickListener {
                isFavoris = !isFavoris
                if (isFavoris){
                    buttonFavoris.setImageResource(R.drawable.favoris_true)
                    Data.obtenirLivreParISBN(isbn)?.let { it1 -> Data.ajouterLivreFavori(it1) }
                } else {
                    buttonFavoris.setImageResource(R.drawable.favoris_false)
                    Data.retirerLivreFavoriParISBN(isbn)
                }
            }
        }
    }
}