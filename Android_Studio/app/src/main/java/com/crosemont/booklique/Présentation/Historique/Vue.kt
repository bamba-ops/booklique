package com.crosemont.booklique.Présentation.Historique

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.crosemont.booklique.R

class Vue : Fragment() {

    lateinit var resultatHistoriqueResrvation: LinearLayout
    private lateinit var présentateur: Présentateur

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_historique, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultatHistoriqueResrvation = view.findViewById(R.id.resultatHistoriqueReservation)
        présentateur = Présentateur(this)
        présentateur.afficherHistoriqueReservation()
    }

    fun afficherHistoriqueReservation(historiqueUIList: List<Modèle>) {
        resultatHistoriqueResrvation.removeAllViews()
        for (historique in historiqueUIList) {
            val itemView = LayoutInflater.from(context).inflate(
                R.layout.item_historique,
                resultatHistoriqueResrvation,
                false
            )
            val titre: TextView = itemView.findViewById(R.id.idTitre)
            val dateReservation: TextView = itemView.findViewById(R.id.dateRerservation)
            val dateRetour: TextView = itemView.findViewById(R.id.dateRetour)

            titre.text = historique.titre
            dateReservation.text = historique.dateReservation
            dateRetour.text = historique.dateRetour

            resultatHistoriqueResrvation.addView(itemView)
        }
    }
}