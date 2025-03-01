package com.crosemont.booklique.domaine.entité

import java.util.Date

class Reservation(
    val id: Int?,
    val debut: Date,
    val termine: Date,
    val livreIsbn: String)