package com.crosemont.booklique.sourcededonnées.dao.dbConfig

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crosemont.booklique.sourcededonnées.dao.FavorisDao
import com.crosemont.booklique.sourcededonnées.dao.RechercheDao
import com.crosemont.booklique.sourcededonnées.dao.ReservationHistoriqueDao
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.entité.Recherche
import com.crosemont.booklique.domaine.entité.ReservationHistorique

@Database(entities = [Favoris::class, Recherche::class, ReservationHistorique::class], version = 3, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favorisDao(): FavorisDao
    abstract fun rechercheDao(): RechercheDao
    abstract fun reservationHistoriqueDao(): ReservationHistoriqueDao
}
