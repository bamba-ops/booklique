package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.CalendarContract
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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

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
    private lateinit var livre: Livre

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

        présentateur.traiter_afficher_livre()

        buttonReservation.setOnClickListener {
            présentateur.traiter_confirmation_réservation()
        }

        buttonFavoris.setOnClickListener {
            présentateur.traiter_favoris(livre)
        }

        buttonAjouterAgenda.setOnClickListener {
            présentateur.traiterAfficherCalendrier()
        }

    }


    fun afficherLivre(livre: Livre) {
        this.livre = livre
        titreLivre.text = livre.titre
        statutLivre.text = if (livre.estDisponible()) getString(R.string.disponible) else getString(R.string.indisponible)
        descriptionCourte.text = livre.description.take(45) + "..."
        descriptionComplete.text = livre.description
        auteurLivre.text = livre.auteur
        editeurLivre.text = livre.editeur
        datePublicationLivre.text = présentateur.getFormattedDate(livre.date_publication)
        nombrePagesLivre.text = livre.nombre_pages.toString()
        echeanceLivre.text = getString(R.string.non_definit)
        isbnLivre = livre.isbn
        this.livre = livre
        // Charger l'image
        Glide.with(this)
            .load(livre.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageLivre)

        présentateur.traiter_boutton_favoris(livre.estDisponible())

        présentateur.traiter_est_Favori(livre.isbn)
    }

    fun afficher_boutton_reservation(){
        buttonReservation.isEnabled = true
    }

    fun enlever_boutton_reservation(){
        buttonReservation.isEnabled = false
    }

    fun changer_isFavoris(isFavori: Boolean){
        this.isFavoris = isFavori
    }

    fun afficher_favoris(){
        buttonFavoris.setImageResource(R.drawable.favoris_true)
    }

    fun enlever_favoris(){
        buttonFavoris.setImageResource(R.drawable.favoris_false)
    }

    fun afficher_echance_livre(){
        val dateÉchéance = présentateur.écheance()
        sectionEcheance.visibility = View.VISIBLE
        echeanceLivre.text = présentateur.getFormattedDate(dateÉchéance)
    }

    fun avoir_isFavoris(): Boolean {
        return isFavoris
    }




    fun afficherConfirmationReservation() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmation")
        builder.setMessage("Êtes-vous sûr de vouloir continuer ?")

        builder.setPositiveButton("Oui") { dialog, _ ->
            présentateur.traiter_reservation(isbnLivre, livre)
            dialog.dismiss()
        }

        builder.setNegativeButton("Non") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

     fun naviguer_accueil() {
         findNavController().navigate(R.id.action_detail_livre_to_accueil)
    }

    fun afficherCalendrier() {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, afficherTitre())
            putExtra(CalendarContract.Events.DESCRIPTION, afficherDescription())
            afficherLieu()?.let { putExtra(CalendarContract.Events.EVENT_LOCATION, it) }
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, présentateur.écheance().time)
        }
        requireContext().startActivity(intent)
    }


    fun afficherToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun afficherTitre() : String{
        return titreLivre.text?.toString()?.takeIf { it.isNotBlank() } ?: "Booklique"
    }

    fun afficherDescription() : String{
        return getString(R.string.message_description_calandar)
    }

    fun afficherLieu() : String{
        return "6400 16e Avenue, Montréal, QC H1X 2S9"
    }

    fun afficherDialogueConnexion(){
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Connexion internet perdue")
            .setMessage("Veuillez vous reconnecter")
            .setNegativeButton("OK"){
                    dialog, which -> dialog.dismiss()
            }.show()
    }

    @SuppressLint("ServiceCast")
    fun connexion() : Boolean{
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


}
