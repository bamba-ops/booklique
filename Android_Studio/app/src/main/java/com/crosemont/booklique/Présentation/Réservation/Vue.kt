package com.crosemont.booklique.Presentation.Reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.Présentation.Réservation.Présentateur
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.mork_data.Data
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Locale

class Vue : Fragment() {

    private lateinit var présentateur: Présentateur
    private lateinit var vuelistereservation: LinearLayout
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_liste_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vuelistereservation = view.findViewById(R.id.vue_liste_reservation)
        présentateur = Présentateur(this)

        // Récupération des données de réservation de démonstration
        //val reservations = Data.obtenirReservationsDemo()

        //afficherListeRéservation(reservations)

    }


    fun afficherListeRéservation(reservations: List<Reservation>){
        for(reservation in reservations){
            val vueListeReservationView = LayoutInflater.from(context).inflate(R.layout.vue_liste_reservation, vuelistereservation, false)

            val imageReservation: ImageView = vueListeReservationView.findViewById(R.id.image_livre_reserver)
            val titreLivreReserver: TextView = vueListeReservationView.findViewById(R.id.titre_livre_reserver)
            val date_reservation: TextView = vueListeReservationView.findViewById(R.id.date_reservation)
            val date_remise_livre: TextView = vueListeReservationView.findViewById(R.id.date_remise_livre)
            val btn_annuler_reservation: Button = vueListeReservationView.findViewById(R.id.btn_annuler_reservation)
            val btn_prolonger_reservation: Button = vueListeReservationView.findViewById(R.id.btn_prolonger_reservation)


//            Picasso.get()
//                .load(reservation.livre.image_url)
//                .placeholder(R.drawable.placeholder_image)
//                .error(R.drawable.error_image)
//                .into(imageReservation)
//
//            titreLivreReserver.text = reservation.livre.titre
            date_reservation.text = dateFormat.format(reservation.debut)
            date_remise_livre.text = dateFormat.format(reservation.termine)

            btn_annuler_reservation.setOnClickListener {

            }

            btn_prolonger_reservation.setOnClickListener {

            }


            vuelistereservation.addView(vueListeReservationView)

        }
    }
}
