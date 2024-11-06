package com.crosemont.booklique.domaine.entité

import Livre
import java.util.Date

class Reservation(
    val id: Int,
    val debut: Date,
    val termine: Date,
    val livre: Livre) {
}