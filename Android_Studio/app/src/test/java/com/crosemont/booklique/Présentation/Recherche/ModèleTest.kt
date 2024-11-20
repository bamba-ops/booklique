package com.crosemont.booklique.Présentation.Recherche

import com.crosemont.booklique.Présentation.Recherche.Modèle

import Livre
import com.crosemont.booklique.domaine.mork_data.Data
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class ModèleTest{

    private val livresMorck: List<Livre> = listOf(
        Livre(
            isbn = "978-3-16-148410-0",
            image_url = "https://example.com/image1.jpg",
            titre = "Le Mystère de la Forêt",
            description = "Un roman captivant sur les secrets d'une forêt mystérieuse.",
            auteur = "Jean Dupont",
            editeur = "Éditions Nature",
            genre = "Aventure",
            date_publication = Date(120, 5, 15), // 15 juin 2020 (année = 2020 - 1900)
            nombre_pages = 320,
            quantite = 10
        ),
        Livre(
            isbn = "978-0-14-312774-1",
            image_url = "https://example.com/image2.jpg",
            titre = "Les Étoiles Cachées",
            description = "Une plongée fascinante dans les secrets de l'univers.",
            auteur = "Marie Lemoine",
            editeur = "Cosmos Press",
            genre = "Science-fiction",
            date_publication = Date(121, 10, 10), // 10 novembre 2021
            nombre_pages = 250,
            quantite = 5
        ),
        Livre(
            isbn = "978-1-86197-876-9",
            image_url = "https://example.com/image3.jpg",
            titre = "Histoires de la Mer",
            description = "Une collection d'histoires sur les aventures maritimes.",
            auteur = "Paul Marin",
            editeur = "Oceanic Publishing",
            genre = "Aventure",
            date_publication = Date(119, 2, 20), // 20 mars 2019
            nombre_pages = 400,
            quantite = 8
        )
    )

    @Test
    fun `etant donn`() {
        val livres = livresMorck

        val model = Modèle(livres)

        val livresParAuteur = model.obtenirLivresParAuteur("Paul Marin");

        assertEquals(livresParAuteur[0].auteur, "Paul Marin")

    }
}