package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.content.Context
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.service.LivreService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.Date
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ModèleTest {

    private lateinit var modèle: Modèle
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        mockContext = mock(Context::class.java)
        modèle = Modèle(mockContext)
    }

    @Test
    fun `obtenirLivresParAuteur retourne une liste de livres quand isObtenirLivreParAuteur est vrai`() {
        // Arrange
        val auteur = "Paul Marin"
        LivreService.isObtenirLivreParAuteur = true
        LivreService._obtenirLivreParAuteur = auteur
        val livresAttendus = listOf(
            Livre(
                isbn = "123456789",
                image_url = "",
                titre = "Livre de Paul",
                description = "",
                auteur = auteur,
                editeur = "",
                genre = "",
                date_publication = Date(),
                nombre_pages = 100,
                quantite = 1
            )
        )
        LivreService::class.java.getDeclaredMethod("obtenirLivresParAuteur", String::class.java)
        `when`(LivreService.obtenirLivresParAuteur(auteur)).thenReturn(livresAttendus)

        // Act
        val résultat = modèle.obtenirLivresParAuteur()

        // Assert
        assertEquals(livresAttendus, résultat)
        assertEquals(true, modèle.isObtenirLivreParAuteur)
        assertEquals(false, LivreService.isObtenirLivreParAuteur)
    }

    @Test
    fun `obtenirLivreParTitre retourne un livre quand isObtenirLivreParTitre est vrai`() {
        // Arrange
        val titre = "Histoires de la Mer"
        LivreService.isObtenirLivreParTitre = true
        LivreService._obtenirLivreParTitre = titre
        val livreAttendu = Livre(
            isbn = "123456789",
            image_url = "",
            titre = titre,
            description = "",
            auteur = "",
            editeur = "",
            genre = "",
            date_publication = Date(),
            nombre_pages = 200,
            quantite = 2
        )
        `when`(LivreService.obtenirLivreParTitre(titre)).thenReturn(livreAttendu)

        // Act
        val résultat = modèle.obtenirLivreParTitre()

        // Assert
        assertEquals(livreAttendu, résultat)
        assertEquals(true, modèle.isObtenirLivreParTitre)
        assertEquals(false, LivreService.isObtenirLivreParTitre)
    }

    @Test
    fun `obtenirLivresParNouveautes retourne une liste de livres quand isObtenirLivresParNouveautes est vrai`() {
        // Arrange
        LivreService.isObtenirLivresParNouveautes = true
        val livresAttendus = listOf(
            Livre(
                isbn = "123456789",
                image_url = "",
                titre = "Nouveauté 1",
                description = "",
                auteur = "",
                editeur = "",
                genre = "",
                date_publication = Date(),
                nombre_pages = 300,
                quantite = 5
            ),
            Livre(
                isbn = "987654321",
                image_url = "",
                titre = "Nouveauté 2",
                description = "",
                auteur = "",
                editeur = "",
                genre = "",
                date_publication = Date(),
                nombre_pages = 250,
                quantite = 3
            )
        )
        `when`(LivreService.obtenirLivresParNouveautesPrend10()).thenReturn(livresAttendus)

        // Act
        val résultat = modèle.obtenirLivresParNouveautes()

        // Assert
        assertEquals(livresAttendus, résultat)
        assertEquals(true, modèle.isObtenirLivreParNouveautes)
        assertEquals(false, LivreService.isObtenirLivresParNouveautes)
    }

    @Test
    fun `_obtenirLivresParGenre retourne une liste de livres quand isObtenirLivresParGenre est vrai`() {
        // Arrange
        val genre = "Aventure"
        LivreService.isObtenirLivresParGenre = true
        LivreService._obtenirLivresParGenre = genre
        val livresAttendus = com.crosemont.booklique.domaine.mork_data.Data.obtenirLivresDemo()
        `when`(LivreService.obtenirLivresParGenre(genre)).thenReturn(livresAttendus)

        // Act
        val résultat = modèle._obtenirLivresParGenre()

        // Assert
        assertEquals(livresAttendus, résultat)
        assertEquals(true, modèle.isObtenirLivreParGenre)
        assertEquals(false, LivreService.isObtenirLivresParGenre)
    }

    @Test
    fun `retirerLivreFavori retire un favori`() = runBlocking {
        // Arrange
        val isbn = "978-1-86197-876-9"

        // Act
        modèle.retirerLivreFavori(isbn)

        // Assert
        verify(modèle).retirerLivreFavori(isbn)
    }
}
