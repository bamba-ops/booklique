

package com.crosemont.booklique.Présentation.Favoris

import Livre
import com.crosemont.booklique.domaine.mork_data.Data
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class ModèleTest {

    private val livresMorck: List<Livre> = listOf(
        Livre(
            isbn = "978-3-16-148410-0",
            image_url = "https://example.com/image1.jpg",
            titre = "Le Mystère de la Forêt",
            description = "Un roman captivant sur les secrets d'une forêt mystérieuse.",
            auteur = "Jean Dupont",
            editeur = "Éditions Nature",
            genre = "Aventure",
            date_publication = Date(120, 5, 15), // 15 juin 2020
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
    fun `étant donné un Modèle nouvellement instancié lorsqu'on récupère un livre favori par ISBN, on obtient le livre correspondant à l'ISBN`() {
        val model = Modèle()

        // Ajout d'un livre aux favoris
        val livre = livresMorck[0]
        model.ajouterLivresFavoris(livre)

        val livreFavori = model.obtenirLivreFavorisParISBN(livre.isbn)

        assertEquals(livre.isbn, livreFavori?.isbn)
        assertEquals(livre.titre, livreFavori?.titre)
    }

    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'on ajoute un livre aux favoris, alors il est ajouté à la liste des favoris`() {
        val model = Modèle()

        // Ajout d'un livre aux favoris
        val livre = livresMorck[1]
        model.ajouterLivresFavoris(livre)

        val livreFavori = model.obtenirLivreFavorisParISBN(livre.isbn)

        assertEquals(livre.isbn, livreFavori?.isbn)
    }

    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'on retire un livre des favoris, alors il est retiré de la liste des favoris`() {
        val model = Modèle()

        // Ajout du livre aux favoris puis retirer
        val livre = livresMorck[2]
        model.ajouterLivresFavoris(livre)
        model.retirerLivreFavorisParISBN(livre.isbn)

        val livreFavoriRetire = model.obtenirLivreFavorisParISBN(livre.isbn)

        assertEquals(null, livreFavoriRetire)
    }

    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'on obtient la liste des livres favoris, alors la liste est correcte`() {
        val model = Modèle()

        // Ajout de livres aux favoris
        val livre1 = livresMorck[0]
        val livre2 = livresMorck[1]
        model.ajouterLivresFavoris(livre1)
        model.ajouterLivresFavoris(livre2)

        val livresFavoris = model.obtenirLivresFavoris()

        assertEquals(2, livresFavoris.size)
        assertEquals(livre1.isbn, livresFavoris[0].isbn)
        assertEquals(livre2.isbn, livresFavoris[1].isbn)
    }
}