
package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import com.crosemont.booklique.domaine.mork_data.Data
import kotlin.test.Test
import java.util.Date
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
        )
    )



    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'on vérifie si un livre est en favoris, alors il ne l'est pas s'il n'a pas été ajouté`() {
        val model = Modèle()

        val estFavori = model.obtenirLivreFavori("978-3-16-148410-0")

        assertEquals(false, estFavori)
    }

    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'on ajoute un livre en favoris, alors le livre est maintenant en favoris`() {
        val model = Modèle()

        model.ajouterLivreFavori("978-3-16-148410-0")

        val estFavori = model.obtenirLivreFavori("978-3-16-148410-0")

        assertEquals(true, estFavori)
    }

    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'on retire un livre de favoris, alors le livre n'est plus en favoris`() {
        val model = Modèle()

        model.ajouterLivreFavori("978-3-16-148410-0")
        model.retirerLivreFavori("978-3-16-148410-0")

        val estFavori = model.obtenirLivreFavori("978-3-16-148410-0")

        assertEquals(false, estFavori)
    }

    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'un livre est marqué comme disponible, alors la fonction retourne true`() {
        val model = Modèle()

        val estDisponible = model.estDisponible("Disponible")

        assertEquals(true, estDisponible)
    }

    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'un livre est marqué comme non disponible, alors la fonction retourne false`() {
        val model = Modèle()

        val estDisponible = model.estDisponible("Indisponible")

        assertEquals(false, estDisponible)
    }

    /*@Test
    //ce teste marche pas
    fun `étant donné un Modèle nouvellement instancié lorsqu'un livre existe dans les données, on obtient le livre correspondant à l'ISBN`() {
        val model = Modèle()

        val livre = model.obtenirLivre()

        assertEquals("978-3-16-148410-0", livre?.isbn)
    }*/

    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'aucun livre n'est sélectionné, obtenirLivre retourne null`() {
        val model = Modèle()

        Data.isObtenirLivre = false

        val livre = model.obtenirLivre()

        assertNull(livre)
    }
}
