package com.crosemont.booklique.Présentation.DetailLivre

import Livre
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

class Présentateur(private val vue: Vue) {

    private val modèle = Modèle(vue.requireContext())
    private var job: Job? = null

    fun traiter_afficher_livre() {
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
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

    fun traiter_est_Favori(isbn: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val favori = withContext(Dispatchers.IO) { modèle.obtenirLivreFavori(isbn) }
            val isFavori = favori != null
            vue.changer_isFavoris(isFavori)
            traiter_favoris_image(isFavori)
        }
    }

    private fun traiter_favoris_image(estFavori: Boolean){
        if(estFavori){
            vue.afficher_favoris()
        } else {
            vue.enlever_favoris()
        }
    }

    fun getFormattedDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun traiter_boutton_favoris(estDisponible: Boolean){
       if(estDisponible){
           vue.afficher_boutton_reservation()
       } else {
           vue.enlever_boutton_reservation()
       }
    }

    fun traiter_favoris(livre: Livre) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val actuelFavori = vue.avoir_isFavoris()
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
            vue.changer_isFavoris(!actuelFavori)
            traiter_favoris_image(!actuelFavori)
        }
    }

    fun écheance(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MONTH, 1)
        return calendar.time
    }

    fun traiterAfficherCalendrier(){
        vue.afficherCalendrier()
    }


}
