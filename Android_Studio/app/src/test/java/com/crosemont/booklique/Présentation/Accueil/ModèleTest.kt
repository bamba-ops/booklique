package com.crosemont.booklique.Présentation.Accueil

import kotlin.test.*
import kotlinx.coroutines.*
import io.mockk.*
import org.junit.After
import org.junit.Before
import java.util.Date
import com.crosemont.booklique.domaine.service.LivreService
import Livre
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

class ModèleTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivreParNouveautes, on obtient une liste de livres`(): Unit = runTest {
        val livresMockés = listOf(
            Livre("ISBN1", "Image1", "Titre1", "Description1", "Auteur1", "Editeur1", "Genre1", Date(122, 1, 15), 5, 30),
            Livre("ISBN2", "Image2", "Titre2", "Description2", "Auteur2", "Editeur2", "Genre2", Date(122, 2, 14), 3, 40)
        )
        mockkObject(LivreService)
        every { LivreService.obtenirLivresParNouveautesPrend5() } returns livresMockés

        val modèle = Modèle()
        val résultat = modèle.obtenirLivreParNouveautes()

        assertEquals(livresMockés, résultat)
        verify { LivreService.obtenirLivresParNouveautesPrend5() }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivresParAuteur, on obtient une liste de livres`(): Unit = runTest {
        val livresMockés = listOf(
            Livre("ISBN3", "Image3", "Titre3", "Description3", "Auteur3", "Editeur3", "Genre3", Date(122, 1, 15), 10, 20)
        )
        mockkObject(LivreService)
        every { LivreService.obtenirLivresParAuteur() } returns livresMockés

        val modèle = Modèle()
        val résultat = modèle.obtenirLivresParAuteur()

        assertEquals(livresMockés, résultat)
        verify { LivreService.obtenirLivresParAuteur() }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivreParAuteur, la méthode est appelée avec le bon auteur`() {
        val auteur = "AuteurCible"

        mockkObject(LivreService)
        every { LivreService.definirLivreParAuteur(auteur) } just Runs

        val modèle = Modèle()
        modèle.obtenirLivreParAuteur(auteur)

        verify { LivreService.definirLivreParAuteur(auteur) }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivreParNouveaute, la méthode est appelée avec le bon ISBN`() {
        val isbn = "ISBN12345"

        mockkObject(LivreService)
        every { LivreService.definirLivre(isbn) } just Runs

        val modèle = Modèle()
        modèle.obtenirLivreParNouveaute(isbn)

        verify { LivreService.definirLivre(isbn) }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivresParNouveautes, la méthode est appelée correctement`() {
        mockkObject(LivreService)
        every { LivreService.definirLivresParNouveautes() } just Runs

        val modèle = Modèle()
        modèle.obtenirLivresParNouveautes()

        verify { LivreService.definirLivresParNouveautes() }
    }
}
