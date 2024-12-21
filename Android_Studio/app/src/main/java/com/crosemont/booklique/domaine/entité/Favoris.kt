package com.crosemont.booklique.domaine.entité

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoris")
data class Favoris(
    @PrimaryKey() val isbn: String,
    val image_url: String,
    val titre: String,
    val description: String,
    val auteur: String,
    val editeur: String,
    val genre: String,
)
