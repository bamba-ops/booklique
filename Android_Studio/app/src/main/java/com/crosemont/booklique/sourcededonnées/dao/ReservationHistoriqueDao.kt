package com.crosemont.booklique.sourcededonnées.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crosemont.booklique.domaine.entité.ReservationHistorique

@Dao
interface ReservationHistoriqueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ajouterReservationHistorique(reservationHistorique: ReservationHistorique)

    @Query("SELECT * FROM reservation WHERE id = :id")
    suspend fun obtenirReservationParId(id: Int): ReservationHistorique?

    @Query("SELECT * FROM reservation")
    suspend fun obtenirToutesLesReservations(): List<ReservationHistorique>

    @Delete
    suspend fun supprimerReservation(reservation: ReservationHistorique)

    @Query("DELETE FROM reservation")
    suspend fun supprimerToutesLesReservations()
}