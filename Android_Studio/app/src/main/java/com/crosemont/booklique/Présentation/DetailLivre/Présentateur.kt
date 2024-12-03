package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import android.content.ActivityNotFoundException
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import com.crosemont.booklique.domaine.entité.Favoris
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Présentateur(private val vue: Vue, context: Context) {

    private val modèle = Modèle(context)

    fun initialiserLivre() {
        vue.afficherLivre(modèle.obtenirLivre() ?: return)
    }

    fun estFavori(isbn: String) {
        CoroutineScope( Dispatchers.Main ).launch {
            var favori: Favoris? = modèle.obtenirLivreFavori(isbn)
            if(favori != null){
                vue.mettreÀJourFavori(true)
            } else {
                vue.mettreÀJourFavori(false)
            }
        }
    }

    fun getFormattedDate(date : Date): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun basculerFavori(livre: Livre) {
        val actuelFavori = vue.estLivreFavori()
        CoroutineScope( Dispatchers.Main ).launch {
            if (actuelFavori){
                modèle.retirerLivreFavori(livre.isbn)
            } else{
                modèle.ajouterLivreFavori(Favoris(
                    livre.isbn,
                    livre.image_url,
                    livre.titre,
                    livre.description,
                    livre.auteur,
                    livre.editeur,
                    livre.genre
                ))
            }
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
