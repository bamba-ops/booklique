package com.crosemont.booklique.domaine.dao.dbConfig

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun obtenirInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "booklique_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
