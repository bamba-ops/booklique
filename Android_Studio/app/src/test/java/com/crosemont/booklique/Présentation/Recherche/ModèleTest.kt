package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.content.Context
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.entité.Recherche
import com.crosemont.booklique.domaine.service.LivreService
import com.crosemont.booklique.sourcededonnées.dao.FavorisDao
import com.crosemont.booklique.sourcededonnées.dao.RechercheDao
import com.crosemont.booklique.sourcededonnées.dao.dbConfig.AppDatabase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ModèleTest {

    private lateinit var mockContext: Context
    private lateinit var mockDatabase: AppDatabase
    private lateinit var mockFavorisDao: FavorisDao
    private lateinit var mockRechercheDao: RechercheDao
    private lateinit var modèle: Modèle

    @Before
    fun setUp() {
        mockContext = mockk(relaxed = true)
        mockDatabase = mockk(relaxed = true)
        mockFavorisDao = mockk(relaxed = true)
        mockRechercheDao = mockk(relaxed = true)

        mockkStatic(androidx.room.Room::class)
        every { androidx.room.Room.databaseBuilder(mockContext, AppDatabase::class.java, "app_database").build() } returns mockDatabase
        every { mockDatabase.favorisDao() } returns mockFavorisDao
        every { mockDatabase.rechercheDao() } returns mockRechercheDao

        modèle = Modèle(mockContext)
    }




    @Test
    fun `test obtenirLivresParNomAuteur calls LivreService`() {
        val auteur = "Auteur Test"
        mockkObject(LivreService)
        every { LivreService.definirLivreParAuteur(auteur) } just Runs

        modèle.obtenirLivresParNomAuteur(auteur)

        verify { LivreService.definirLivreParAuteur(auteur) }
    }

    @Test
    fun `test obtenirLivresParNomTitre calls LivreService`() {
        val titre = "Titre Test"
        mockkObject(LivreService)
        every { LivreService.definirLivreParTitre(titre) } just Runs

        modèle.obtenirLivresParNomTitre(titre)

        verify { LivreService.definirLivreParTitre(titre) }
    }

    @Test
    fun `test obtenirLivresParTitres returns expected list`() {
        val titres = listOf("Titre1", "Titre2")
        mockkObject(LivreService)
        every { LivreService.obtenirLivresParTires() } returns titres

        val result = modèle.obtenirLivresParTitres()

        assertEquals(titres, result)
        verify { LivreService.obtenirLivresParTires() }
    }

    @Test
    fun `test obtenirLivresParAuteur returns expected list`() {
        val livres = listOf(mockk<Livre>(), mockk<Livre>())
        mockkObject(LivreService)
        every { LivreService.isObtenirLivreParAuteur } returns true
        every { LivreService._obtenirLivreParAuteur } returns "Auteur Test"
        every { LivreService.obtenirLivresParAuteur(any()) } returns livres

        val result = modèle.obtenirLivresParAuteur()

        assertEquals(livres, result)
        verify { LivreService.obtenirLivresParAuteur(any()) }
    }


}
