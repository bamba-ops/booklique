package com.crosemont.booklique.Présentation.Historique

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class Présentateur(private val vue: Vue) {
    private val modèle = Modèle(vue.requireContext())

    fun formaterDateHistorique(date: Date?): String {
        if (date == null) {
            return "Date invalide"
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun afficherHistoriqueReservation() {
        if(!vue.connexion()){
            vue.afficherDialogueConnexion()
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
}