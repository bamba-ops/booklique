package com.crosemont.booklique.Présentation.Historique

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.entité.ReservationHistorique

class Vue : Fragment() {

    lateinit var resultatHistoriqueResrvation: LinearLayout
    private lateinit var présentateur: Présentateur
    private lateinit var boutonSupprimerHistorique: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_historique, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultatHistoriqueResrvation = view.findViewById(R.id.resultatHistoriqueReservation)
        boutonSupprimerHistorique = view.findViewById(R.id.btnSupprimerHistorique)
        présentateur = Présentateur(this, requireContext())
        présentateur.afficherHistoriqueReservation()

        boutonSupprimerHistorique.setOnClickListener {
            présentateur.supprimerHistoriqueReservation() // Appelle la méthode pour supprimer
        }

    }

    fun afficherHistoriqueReservation(reservationHistorique: List<ReservationHistorique>) {
        resultatHistoriqueResrvation.removeAllViews()
        for (historique in reservationHistorique) {
            val itemView = LayoutInflater.from(context).inflate(
                R.layout.item_historique,
                resultatHistoriqueResrvation,
                false
            )
            val titre: TextView = itemView.findViewById(R.id.idTitre)
            val dateReservation: TextView = itemView.findViewById(R.id.dateRerservation)
            val dateRetour: TextView = itemView.findViewById(R.id.dateRetour)

            présentateur.traiter_titre_historique_reservation(historique.livreIsbn, titre)
            dateReservation.text = présentateur.formaterDateHistorique(historique.debut)
            dateRetour.text = présentateur.formaterDateHistorique(historique.termine)

            resultatHistoriqueResrvation.addView(itemView)
        }
    }

    fun changer_text(titre: TextView, livreTitre: String){
        titre.text = livreTitre
    }
}