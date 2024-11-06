package com.crosemont.booklique.Presentation.Reservation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.entité.Reservation
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationAdapter(context: Context, reservations: List<Reservation>) :
    ArrayAdapter<Reservation>(context, 0, reservations) {

    // Format de la date en "jour mois année"
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.vue_liste_reservation, parent, false)

        val reservation = getItem(position)

        reservation?.let {
            val imageLivre = view.findViewById<ImageView>(R.id.image_livre_reserver)
            val titreLivre = view.findViewById<TextView>(R.id.titre_livre_reserver)
            val dateReservation = view.findViewById<TextView>(R.id.date_reservation)
            val dateRemise = view.findViewById<TextView>(R.id.date_remise_livre)

            // Affichage de l'image avec Glide
            Glide.with(context).load(it.livre.image_url).into(imageLivre)

            // Affichage des autres données
            titreLivre.text = it.livre.titre
            dateReservation.text = dateFormat.format(it.debut)
            dateRemise.text = dateFormat.format(it.termine)
        }

        return view
    }
}
