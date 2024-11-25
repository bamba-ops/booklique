package com.crosemont.booklique.Présentation.Accueil

import kotlin.test.*
import kotlinx.coroutines.*
import org.mockito.Mockito
import kotlinx.coroutines.test.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith
import com.crosemont.booklique.domaine.mork_data.Data
import Livre
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.After
import org.junit.Before
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
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
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivres, on obtient une liste de livres`() = runTest{
        val livresMockés = listOf(
            Livre("ISBN1", "Image1", "titre1", "Description1", "Auteur1", "Editeur1", "Genre1", Date(122, 1, 15), 5, 30),
            Livre("ISBN2", "Image2", "titre2", "Description2", "Auteur2", "Editeur2", "Genre2", Date(122, 2, 14), 3, 40)
        )
        mockkObject(Data.Companion)
        every { Data.obtenirLivresDemo() } returns livresMockés

        val modèle = Modèle()
        val résultat = modèle.obtenirLivres()

        assertEquals(livresMockés, résultat)
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivreParNouveautes, on obtient une liste de livres`() = runTest {
        val livresMockés = listOf(
            Livre("ISBN3", "Image3", "titre3", "Description3", "Auteur3", "Editeur3", "Genre3", Date(122, 1, 15), 10, 20)
        )
        mockkObject(Data.Companion)
        every { Data.obtenirLivresDemo() } returns livresMockés

        val modèle = Modèle()
        val résultat = modèle.obtenirLivreParNouveautes()

        assertEquals(livresMockés, résultat)
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivresParAuteur, on obtient une liste de livres classés par auteur`() = runTest {
        val livresMockés = listOf(
            Livre("ISBN4", "Image4", "titre4", "Description3", "Auteur3", "Editeur3", "Genre3", Date(122, 1, 15), 10, 20)
        )
        mockkObject(Data.Companion)
        every { Data.obtenirLivresParAuteurs() } returns livresMockés

        val modèle = Modèle()
        val résultat = modèle.obtenirLivresParAuteur()

        assertEquals(livresMockés, résultat)
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on définit un livre par auteur, la méthode Data est appelée avec le bon auteur`() {
        val auteur = "Auteur1"

        mockkObject(Data.Companion)
        every { Data.definirLivreParAuteur(auteur) } just Runs

        val modèle = Modèle()
        modèle.obtenirLivreParAuteur(auteur)

        verify { Data.definirLivreParAuteur(auteur) }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on définit un livre par nouveauté, la méthode Data est appelée avec le bon ISBN`() {
        val isbn = "ISBN123"

        mockkObject(Data.Companion)
        every { Data.definirLivre(isbn) } just Runs

        val modèle = Modèle()
        modèle.obtenirLivreParNouveaute(isbn)

        verify { Data.definirLivre(isbn) }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on définit des livres par nouveautés, la méthode Data est appelée`() {
        mockkObject(Data.Companion)
        every { Data.definirLivresParNouveautes() } just Runs

        val modèle = Modèle()
        modèle.obtenirLivresParNouveautes()

        verify { Data.definirLivresParNouveautes() }
    }
}