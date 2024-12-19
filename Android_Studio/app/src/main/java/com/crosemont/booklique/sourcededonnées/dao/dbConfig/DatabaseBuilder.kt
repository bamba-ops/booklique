package com.crosemont.booklique.sourcededonnées.dao.dbConfig

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseBuilder {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE recherche (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, requete TEXT NOT NULL)")
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
                CREATE TABLE reservation (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    debut INTEGER NOT NULL,
                    termine INTEGER NOT NULL,
                    livreIsbn TEXT NOT NULL
                )
            """)
        }
    }

    fun obtenirInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "booklique_database"
            ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
            INSTANCE = instance
            instance
        }
    }
}
