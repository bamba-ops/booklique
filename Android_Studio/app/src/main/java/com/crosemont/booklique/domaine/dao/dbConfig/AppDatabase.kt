package com.crosemont.booklique.domaine.dao.dbConfig

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crosemont.booklique.domaine.dao.FavorisDao
import com.crosemont.booklique.domaine.entité.Favoris

@Database(entities = [Favoris::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favorisDao(): FavorisDao
}
