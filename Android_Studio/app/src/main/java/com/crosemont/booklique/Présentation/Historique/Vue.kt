package com.crosemont.booklique.Présentation.Historique

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
//import com.crosemont.booklique.ARG_PARAM1
//import com.crosemont.booklique.ARG_PARAM2
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.mork_data.Data
import org.w3c.dom.Text
import java.sql.RowId
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [Vue.newInstance] factory method to
 * create an instance of this fragment.
 */
class Vue : Fragment() {

    lateinit var resultatHistoriqueResrvation : LinearLayout
    lateinit var historiqueList : List<Reservation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historique, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultatHistoriqueResrvation = view.findViewById(R.id.resultatHistoriqueReservation)
        historiqueList = Data.obtenirReservationsDemo()

        afficherHistoriqueReservation()
    }

    fun afficherHistoriqueReservation(){
        for(historique in historiqueList){
            val resultatHistoriqueReservationView = LayoutInflater.from(context).inflate(R.layout.item_historique, resultatHistoriqueResrvation, false)

            val titre: TextView = resultatHistoriqueReservationView.findViewById(R.id.idTitre)
            val date_reservation: TextView = resultatHistoriqueReservationView.findViewById(R.id.dateRerservation)
            val date_retour: TextView = resultatHistoriqueReservationView.findViewById(R.id.dateRetour)

            titre.text = historique.livre.titre
            date_reservation.text = formaterDateHistorique(historique.debut)
            date_retour.text = formaterDateHistorique(historique.termine)

            resultatHistoriqueResrvation.addView(resultatHistoriqueReservationView)
        }
    }

    fun formaterDateHistorique(date: Date?): String {
        if (date == null) {
            return "Date invalide"
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }
}