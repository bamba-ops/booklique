package com.crosemont.booklique.Présentation.DetailLivre

import Livre
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
    private lateinit var disponnible: String


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

        présentateur.initialiserLivre()
    }


    fun afficherLivre(livre: Livre){

        titreLivre.text = livre.titre
        statutLivre.text = if (livre.estDisponible()) "Disponible" else "Indisponible"
        descriptionCourte.text = livre.description.take(25).plus("...")
        descriptionComplete.text = livre.description
        auteurLivre.text = livre.auteur
        echeanceLivre.text = livre.date_publication.toString()
        disponnible = (if (livre.estDisponible()) "Disponible" else "Indisponible").toString()
        imageUrl = livre.image_url

        Glide.with(this)
            .load(livre.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageLivre)

        isDisponnible()
        présentateur.estFavori(livre.isbn)

        buttonFavoris.setOnClickListener {
            présentateur.basculerFavori(livre.isbn)
        }
    }

    fun changer_boutton_favoris_source_true(){
        buttonFavoris.setImageResource(R.drawable.favoris_true)
    }

    fun changer_boutton_favoris_source_false(){
        buttonFavoris.setImageResource(R.drawable.favoris_false)
    }

    fun changer_isFavoris_vrai(){
        isFavoris = true
    }

    fun retourner_favori(): Boolean{
        return isFavoris
    }

    fun verifier_isFavoris(){
        isFavoris = !isFavoris
    }

    fun isDisponnible(){
        if (disponnible == "Disponible")
            buttonReservation.isEnabled = true
        else
            buttonReservation.isEnabled = false

    }

}
