package com.crosemont.booklique.domaine.entité

import java.util.Date

class Livre(
    val isbn : String,
    val image_url : String,
    val titre : String,
    val description : String,
    val auteur : String,
    val editeur : String,
    val genre : String,
    date_publication : Date,
    nombre_pages : Int,
    quantite : Int,
    ) {
}