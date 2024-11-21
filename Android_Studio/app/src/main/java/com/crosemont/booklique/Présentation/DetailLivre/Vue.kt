package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.crosemont.booklique.R
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
    private lateinit var buttonAjouterAgenda: ImageButton
    private lateinit var editeurLivre: TextView
    private lateinit var datePublicationLivre: TextView
    private lateinit var nombrePagesLivre: TextView
    private lateinit var sectionEcheance: View
    private lateinit var présentateur: Présentateur

    private var isFavoris: Boolean = false
    private var isbnLivre: String = ""

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
        buttonAjouterAgenda = view.findViewById(R.id.bouton_alert_details)
        editeurLivre = view.findViewById(R.id.editeur_livre_details)
        datePublicationLivre = view.findViewById(R.id.date_publication_livre_details)
        nombrePagesLivre = view.findViewById(R.id.nombre_pages_livre_details)
        sectionEcheance = view.findViewById(R.id.echeance_section)

        présentateur = Présentateur(this)

        // Initialisation des données
        présentateur.initialiserLivre()

        // Gestion des actions

        buttonReservation.setOnClickListener {
            effectuerReservation()
        }

        // Fonctionnalité pour le bouton favoris
        buttonFavoris.setOnClickListener {
            présentateur.basculerFavori(isbnLivre)
        }

        // Fonctionnalité pour l'ajout à l'agenda

        buttonAjouterAgenda.setOnClickListener {
            ouvrirCalendrierPourAjouterEvenement()
        }

    }

    fun afficherLivre(livre: Livre) {
        titreLivre.text = livre.titre
        statutLivre.text = if (livre.estDisponible()) getString(R.string.disponible) else getString(R.string.indisponible)
        descriptionCourte.text = livre.description.take(25) + "..."
        descriptionComplete.text = livre.description
        auteurLivre.text = livre.auteur
        editeurLivre.text = livre.editeur
        datePublicationLivre.text = livre.date_publication.toString()
        nombrePagesLivre.text = livre.nombre_pages.toString()
        echeanceLivre.text = getString(R.string.non_definit)
        isbnLivre = livre.isbn

        // Charger l'image
        Glide.with(this)
            .load(livre.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageLivre)

        mettreAJourBoutonReservation(livre.estDisponible())
        présentateur.estFavori(livre.isbn)
    }

    private fun mettreAJourBoutonReservation(estDisponible: Boolean) {
        buttonReservation.isEnabled = estDisponible
    }

    fun mettreÀJourFavori(isFavori: Boolean) {
        this.isFavoris = isFavori
        buttonFavoris.setImageResource(
            if (isFavoris) R.drawable.favoris_true else R.drawable.favoris_false
        )
    }

    private fun effectuerReservation() {
        val dateÉchéance = présentateur.écheance()
        sectionEcheance.visibility = View.VISIBLE
        echeanceLivre.text = dateÉchéance.toString()
        buttonReservation.text = getString(R.string.confirmer_reservation)
    }

    fun estLivreFavori(): Boolean {
        return isFavoris
    }

    private fun ouvrirCalendrierPourAjouterEvenement() {
        // Récupérer les valeurs des champs de texte pour le titre et la description
        val titre = titreLivre.text?.toString()?.takeIf { it.isNotBlank() } ?: "Booklique"
        val description = getString(R.string.message_description_calandar)

        val lieu = "6400 16e Avenue, Montréal, QC H1X 2S9"

        // Obtenir la date d'échéance depuis le présentateur
        val dateÉchéance = présentateur.écheance()

        // Ouvrir le calendrier pour ajouter un événement avec les informations collectées
        présentateur.ouvrirCalendrierPourAjouterEvenement(
            requireContext(),
            titre,
            description,
            lieu,
            dateÉchéance
        )
    }



    // En cas d'erreur
    fun afficherToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
