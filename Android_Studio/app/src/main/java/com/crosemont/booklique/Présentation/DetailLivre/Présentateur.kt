package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.appcompat.app.AlertDialog
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.entité.ReservationHistorique
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Présentateur(private val vue: Vue, context: Context) {

    private val modèle = Modèle(context)
    private var job: Job? = null

    fun initialiserLivre() {
        if(!modèle.connexion(vue.requireContext())){
            traiterConnexion(vue.requireContext())
        }else{
            job = CoroutineScope(Dispatchers.Main).launch {
                val livre = withContext(Dispatchers.IO) { modèle.obtenirLivre() }
                if (livre != null) {
                    vue.afficherLivre(livre)
                }
            }
        }
    }

    private fun traiter_échance(): Pair<Date, Date> {
        val debut = Date()

        val calendrier = Calendar.getInstance()
        calendrier.time = debut
        calendrier.add(Calendar.MONTH, 1)
        val fin = calendrier.time

        return Pair(debut, fin)
    }

    fun traiter_confirmation_réservation(){
        vue.afficherConfirmationReservation()
    }

    fun traiter_reservation(isbn: String, livre: Livre){
        vue.afficher_echance_livre()
        job = CoroutineScope(Dispatchers.Main).launch {
            val (debut, fin) = traiter_échance()
            livre.quantite -= 1

            val livreReponse = withContext(Dispatchers.IO) { modèle.modifierLivreParIsbn(isbn, livre) }

            if(livreReponse != null){
                val reservation = withContext(Dispatchers.IO) { modèle.ajouterReservation(Reservation(
                    id = null,
                    debut = debut,
                    termine = fin,
                    livreIsbn = isbn
                )) }

                if (reservation != null) {
                    val reservationHistorique = ReservationHistorique(
                        debut = reservation.debut,
                        termine = reservation.termine,
                        livreIsbn = reservation.livreIsbn
                    )

                    withContext(Dispatchers.IO) {
                        modèle.ajouterReservationHistorique(reservationHistorique)
                    }

                    traiter_navigation_accueil()
                }
            }
        }
    }

    fun traiter_navigation_accueil(){
        vue.naviguer_accueil()
    }

    fun estFavori(isbn: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val favori = withContext(Dispatchers.IO) { modèle.obtenirLivreFavori(isbn) }
            vue.mettreÀJourFavori(favori != null)
        }
    }

    fun getFormattedDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun basculerFavori(livre: Livre) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val actuelFavori = vue.estLivreFavori()
            withContext(Dispatchers.IO) {
                if (actuelFavori) {
                    modèle.retirerLivreFavori(livre.isbn)
                } else {
                    modèle.ajouterLivreFavori(
                        Favoris(
                            livre.isbn,
                            livre.image_url,
                            livre.titre,
                            livre.description,
                            livre.auteur,
                            livre.editeur,
                            livre.genre
                        )
                    )
                }
            }
            vue.mettreÀJourFavori(!actuelFavori)
        }
    }

    fun écheance(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MONTH, 1)
        return calendar.time
    }

    fun ouvrirCalendrierPourAjouterEvenement(
        context: Context,
        titre: String,
        description: String,
        lieu: String?,
        fin: Date
    ) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, titre)
            putExtra(CalendarContract.Events.DESCRIPTION, description)
            lieu?.let { putExtra(CalendarContract.Events.EVENT_LOCATION, it) }
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fin.time)
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }else{
            vue.afficherToast("Erreur: Aucune application capable de gérer cet événement.")
        }
    }

    fun traiterConnexion(context : Context){
        AlertDialog.Builder(context)
            .setTitle("Connexion internet perdue")
            .setMessage("Veuillez vous reconnecter")
            .setNegativeButton("OK"){
                    dialog, which -> dialog.dismiss()
            }.show()
    }
}
