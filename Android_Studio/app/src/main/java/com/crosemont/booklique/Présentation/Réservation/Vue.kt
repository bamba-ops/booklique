package com.crosemont.booklique.Presentation.Reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.mork_data.Data

class Vue : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_liste_reservation, container, false)

        // Récupération des données de réservation de démonstration
        val reservations = Data.obtenirReservationsDemo()

        // Configuration de l'adaptateur et du ListView
        val listView = view.findViewById<ListView>(R.id.vue_liste_reservation)
        val adapter = ReservationAdapter(requireContext(), reservations)
        listView.adapter = adapter

        return view
    }
}
