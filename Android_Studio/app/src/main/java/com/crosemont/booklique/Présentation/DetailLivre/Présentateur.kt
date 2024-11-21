package com.crosemont.booklique.Présentation.DetailLivre

import android.content.ActivityNotFoundException
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import java.util.Calendar
import java.util.Date

class Présentateur(private val vue: Vue) {

    private val modèle = Modèle()

    fun initialiserLivre() {
        vue.afficherLivre(modèle.obtenirLivre() ?: return)
    }

    fun estFavori(isbn: String) {
        val estFavori = modèle.obtenirLivreFavori(isbn)
        vue.mettreÀJourFavori(estFavori)
    }

    fun basculerFavori(isbn: String) {
        val actuelFavori = vue.estLivreFavori()
        if (actuelFavori) {
            modèle.retirerLivreFavori(isbn)
        } else {
            modèle.ajouterLivreFavori(isbn)
        }
        vue.mettreÀJourFavori(!actuelFavori)
    }

    // Calculer l'échéance : date actuelle + 1 mois
    fun écheance(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MONTH, 1)
        return calendar.time
    }

    // Fonction pour ouvrir le calendrier et ajouter un événement
    fun ouvrirCalendrierPourAjouterEvenement(
        context: Context,
        titre: String,
        description: String,
        lieu: String?,
        fin: Date,
    ) {

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, titre)
            putExtra(CalendarContract.Events.DESCRIPTION, description)
            lieu?.let { putExtra(CalendarContract.Events.EVENT_LOCATION, it) }
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fin.time)
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Gérer l'exception si aucune application ne gère l'intent
            vue.afficherToast("Erreur: Aucune application capable de gérer cet événement.")
        }
    }
}
