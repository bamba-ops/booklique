package com.crosemont.booklique.domaine.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crosemont.booklique.domaine.entité.Recherche

@Dao
interface RechercheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ajouterRecherche(recherche: Recherche)

    @Query("SELECT * FROM recherche")
    suspend fun obtenirToutRecherche(): List<Recherche>

    @Query("SELECT DISTINCT requete FROM recherche")
    suspend fun obtenirHistoriqueRecherches(): List<String>

    @Query("DELETE FROM recherche")
    suspend fun supprimerTout()

    @Query("DELETE FROM recherche WHERE id = :id")
    suspend fun supprimerRechercheParId(id: Int)

}