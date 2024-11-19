package com.crosemont.booklique.Présentation.DetailLivre

import android.os.Bundle
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
import androidx.fragment.app.Fragment

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
    private lateinit var présentateur: Présentateur
    private var isFavoris: Boolean = false
    private lateinit var imageUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_livre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisation des vues
        imageLivre = view.findViewById(R.id.image_livre_details)
        titreLivre = view.findViewById(R.id.titre_livre_details)
        statutLivre = view.findViewById(R.id.statut_livre)
        descriptionCourte = view.findViewById(R.id.description_court_details)
        descriptionComplete = view.findViewById(R.id.description_complet_livre_detail)
        auteurLivre = view.findViewById(R.id.auteur_livre_details)
        echeanceLivre = view.findViewById(R.id.echeance_livre)
        buttonReservation = view.findViewById(R.id.bouton_reserver_details)
        buttonFavoris = view.findViewById(R.id.bouton_favoris_details)

        présentateur = Présentateur(this)

        // Récupérer les données passées depuis Accueil (à modifier après l'implémentation de mvp sur accueil)
        arguments?.let { bundle ->
            isbn = bundle.getString("isbn").toString()
            titreLivre.text = bundle.getString("titre")
            statutLivre.text = bundle.getString("disponibilite")
            descriptionCourte.text = bundle.getString("description")?.take(25)?.plus("...") ?: "Description non disponible"
            descriptionComplete.text = bundle.getString("description")
            auteurLivre.text = bundle.getString("auteur")
            echeanceLivre.text = bundle.getString("date_publication")
            imageUrl = bundle.getString("image_url").toString()

            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageLivre)

            présentateur.initialiserLivre(isbn)
        }

        buttonFavoris.setOnClickListener {
            présentateur.basculerFavori(isbn)
        }
    }

    fun mettreAJourFavoris(isFavori: Boolean) {
        isFavoris = isFavori
        val icon = if (isFavoris) R.drawable.favoris_true else R.drawable.favoris_false
        buttonFavoris.setImageResource(icon)
    }

    fun mettreAJourDisponibilite(disponible: Boolean) {
        if (disponible) {
            statutLivre.text = "Disponible"
        } else {
            statutLivre.text = "Indisponible"
        }
    }

    fun getDisponibilite(): String {
        return statutLivre.text.toString()
    }
}
