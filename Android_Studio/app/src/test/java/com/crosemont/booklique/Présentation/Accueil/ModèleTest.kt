package com.crosemont.booklique.Présentation.Accueil

import Livre
import kotlin.test.*
import kotlinx.coroutines.*
import io.mockk.*
import org.junit.After
import org.junit.Before
import android.content.Context
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.entité.Recherche
import com.crosemont.booklique.domaine.service.LivreService
import com.crosemont.booklique.sourcededonnées.dao.FavorisDao
import com.crosemont.booklique.sourcededonnées.dao.RechercheDao
import com.crosemont.booklique.sourcededonnées.dao.dbConfig.DatabaseBuilder
import kotlinx.coroutines.test.runTest
import java.util.Date
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

class ModèleTest {

    private lateinit var modèle: Modèle
    private val mockContext: Context = mockk(relaxed = true)
    private val mockRechercheDao: RechercheDao = mockk(relaxed = true)
    private val mockFavorisDao: FavorisDao = mockk(relaxed = true)

    @Before
    fun setUp() {
        mockkObject(DatabaseBuilder)
        every { DatabaseBuilder.obtenirInstance(mockContext).favorisDao() } returns mockFavorisDao
        every { DatabaseBuilder.obtenirInstance(mockContext).rechercheDao() } returns mockRechercheDao
        modèle = Modèle()
    }

    @After
    fun tearDown() {
        unmockkAll()
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


}
