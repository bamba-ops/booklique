package com.crosemont.booklique.Présentation.Réservation

import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import com.crosemont.booklique.Presentation.Reservation.Vue
import com.crosemont.booklique.Présentation.Réservation.Modèle
import kotlinx.coroutines.Job
import java.util.Calendar

class Présentateur(vue: Vue) {
    private var job: Job? = null
    private val modèle = Modèle()


    fun ouvrirCalendrierPourAjouterEvenement(
        context: android.content.Context,
        titre: String,
        description: String,
        lieu: String?,
        heureDebut: Calendar,
        heureFin: Calendar
    ) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, titre)
            putExtra(CalendarContract.Events.DESCRIPTION, description)
            putExtra(CalendarContract.Events.EVENT_LOCATION, lieu)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, heureDebut.timeInMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, heureFin.timeInMillis)
        }

        // Vérifier qu'une application peut gérer cette Intent avant de la lancer
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            // Aucune application ne peut gérer cette Intent
            Toast.makeText(context, "Aucune application de calendrier trouvée", Toast.LENGTH_SHORT).show()
        }
    }
}