package com.crosemont.booklique.domaine.entité

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reservation")
data class ReservationHistorique(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val debut: Date,
    val termine: Date,
    val livreIsbn: String
)
