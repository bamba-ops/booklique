package com.crosemont.booklique.sourcededonnées.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crosemont.booklique.domaine.entité.Favoris

@Dao
interface FavorisDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ajouterFavori(favoris: Favoris)

    @Query("SELECT * FROM favoris")
    suspend fun obtenirTousLesFavoris(): List<Favoris>

    @Query("DELETE FROM favoris WHERE isbn = :isbn")
    suspend fun supprimerFavoriParIsbn(isbn: String)

    @Query("SELECT * FROM favoris WHERE isbn = :isbn LIMIT 1")
    suspend fun obtenirFavoriParIsbn(isbn: String): Favoris?
}