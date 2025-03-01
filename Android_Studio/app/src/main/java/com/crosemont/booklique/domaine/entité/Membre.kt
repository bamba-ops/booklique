package com.crosemont.booklique.domaine.entité

class Membre (
    val prenom: String,
    val nom: String,
    val courriel: String,
    val telephone: String,
    val reservations: List<Reservation>
) {}