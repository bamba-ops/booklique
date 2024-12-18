package com.crosemont.booklique.Présentation.Historique

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.crosemont.booklique.Présentation.Historique.Modèle
import com.crosemont.booklique.domaine.mork_data.Data
import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.entité.ReservationHistorique
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class Présentateur(private val vue: Vue, context: Context) {
    private val modèle = Modèle(context)

    fun formaterDateHistorique(date: Date?): String {
        if (date == null) {
            return "Date invalide"
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun afficherHistoriqueReservation() {
        if(!modèle.connexion(vue.requireContext())){
            traiterConnexion(vue.requireContext())
        }else{
            CoroutineScope( Dispatchers.Main ).launch {
                var reservationHistoriqueList = modèle.obtenirHistoriqueReservation()
                var i = 0
                if (reservationHistoriqueList.isNotEmpty()) {
                    vue.afficherHistoriqueReservation(reservationHistoriqueList, i)
                }
            }
        }
    }

    fun traiter_titre_historique_reservation(isbn: String, index: Int){
        CoroutineScope( Dispatchers.Main ).launch {
            var livre = withContext(Dispatchers.IO) { modèle.obtenirLivreParIsbn(isbn) }
            if(livre != null){
                vue.changer_text(index, livre.titre)
            }
        }
    }

    fun supprimerHistoriqueReservation() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) { modèle.supprimerHistoriqueReservation()}
        }
    }

    fun traiterConnexion(context : Context){
        AlertDialog.Builder(context)
            .setTitle("Connexion internet perdue")
            .setMessage("Veuillez vous reconnecter")
            .setNegativeButton("OK"){
                    dialog, which -> dialog.dismiss()
            }.show()
    }
}