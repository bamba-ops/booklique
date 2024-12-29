package com.crosemont.booklique.domaine.entité

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recherche")
data class Recherche(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val requete: String
)
