package com.crosemont.booklique.Présentation.Recherche

import Livre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class testPrésentateur {
    private val livresMock = listOf(
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
        )
    )

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    private val vue = Vue() // Utilisation de votre vue fournie
    private val présentateur = Présentateur(vue) // Connexion avec le présentateur

    @Test
    fun `ajouter un livre à favoris via vue`() {
        val livre = livresMock[0]
        présentateur.traiter_ajouter_livre_favori(livre)

        vue.préparationAfficherLivres("Favoris")
        vue.afficherLivres(livre)

        assertTrue(présentateur.traiter_est_livre_favori(livre.isbn))
    }

    @Test
    fun `supprimer un livre des favoris via vue`() {
        val livre = livresMock[1]
        présentateur.traiter_ajouter_livre_favori(livre)

        présentateur.traiter_retirer_livre_favori(livre.isbn)

        assertFalse(présentateur.traiter_est_livre_favori(livre.isbn))
    }

    @Test
    fun `recherche de livres par titre via vue`() {
        val titreRecherche = "Le Mystère de la Forêt"

        présentateur.afficherLivresParTitre(titreRecherche)

        vue.préparationAfficherLivres(titreRecherche)
        vue.afficherLivres(livresMock[0])
        assertEquals(titreRecherche, livresMock[0].titre)
    }

    @Test
    fun `recherche de livres par genre via vue`() {
        val genreRecherche = "Historique"

        présentateur.afficherLivresParTitre(genreRecherche)
        vue.afficherChargement(true)

        vue.préparationAfficherLivres(genreRecherche)
        vue.afficherLivres(livresMock[0])
        assertEquals(genreRecherche, livresMock[0].genre)

    }

    @Test
    fun `recherche de livres par auteur via vue`() {
        val auteurRecherche = "Marie Lemoine"

        présentateur.afficherLivresParAuteur(auteurRecherche)

        vue.préparationAfficherLivres(auteurRecherche)
        vue.afficherLivres(livresMock[1])
        assertEquals(auteurRecherche, livresMock[1].auteur)

    }

    @Test
    fun `recherche titre invalide via vue`() {
        val titreRecherche = "Le petit lou"

        présentateur.afficherLivresParAuteur(titreRecherche)

        val message = "Aucun résultat trouvé pour 'Le petit lou'."
        vue.modifierTextRechercheParDefaut(message)
        assertEquals(message, vue.textRechercheParDefaut.text)
    }

    @Test
    fun `recherche auteur invalide via vue`() {
        val auteurRecherche = "Joseph Purshey"

        présentateur.afficherLivresParAuteur(auteurRecherche)

        val message = "Aucun résultat trouvé pour 'Joseph Purshey'."
        vue.modifierTextRechercheParDefaut(message)
        assertEquals(message, vue.textRechercheParDefaut.text)
    }

    @Test
    fun `recherche genre invalide via vue`() {
        // Arrange : Préparation des données et objets nécessaires
        val genreRecherche = "Amour"
        présentateur.afficherLivresParAuteur(genreRecherche)

        val message = "Aucun résultat trouvé pour 'Joseph Purshey'."
        vue.modifierTextRechercheParDefaut(message)
        assertEquals(message, vue.textRechercheParDefaut.text)

    }

    @Test
    fun `recherche vide via vue`() {

        présentateur.lancerRecherche("")

        val message = "Veuillez entrer un texte à rechercher."
        vue.modifierTextRechercheParDefaut(message)
        assertEquals(message, vue.textRechercheParDefaut.text)
    }


}