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
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivresParNouveautes, on obtient une liste de livres`() {
        val livresMockés = listOf(
            Livre("ISBN1", "Image1", "titre1", "Description1", "Auteur1", "Editeur1", "Genre1", Date(122, 1, 15), 5, 30)
        )

        mockkObject(LivreService)
        every { LivreService.isObtenirLivresParNouveautes } returns true
        every { LivreService.obtenirLivresParNouveautesPrend10() } returns livresMockés

        val résultat = modèle.obtenirLivresParNouveautes()

        assertEquals(livresMockés, résultat)
        verify { LivreService.obtenirLivresParNouveautesPrend10() }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on ajoute une recherche, celle-ci est enregistrée`() = runTest {
        val requete = "RechercheTest"

        modèle.ajouterRecherche(requete)

        coVerify { mockRechercheDao.ajouterRecherche(Recherche(requete = requete)) }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on supprime l'historique, il est vidé`() = runTest {
        modèle.supprimerHistoriqueRecherche()

        coVerify { mockRechercheDao.supprimerTout() }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on appelle obtenirHistoriqueRecherches, on obtient une liste`() = runTest {
        val historiqueMocké = listOf("Recherche1", "Recherche2")
        coEvery { mockRechercheDao.obtenirHistoriqueRecherches() } returns historiqueMocké

        val résultat = modèle.obtenirHistoriqueRecherches()

        assertEquals(historiqueMocké, résultat)
        coVerify { mockRechercheDao.obtenirHistoriqueRecherches() }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on ajoute un livre favori, il est sauvegardé`() = runTest {
        val favori = Favoris("ISBN1", "Titre1", "Auteur1", "Image1")

        modèle.ajouterLivreFavori(favori)

        coVerify { mockFavorisDao.ajouterFavori(favori) }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on supprime un livre favori, il est retiré`() = runTest {
        val isbn = "ISBN1"

        modèle.retirerLivreFavori(isbn)

        coVerify { mockFavorisDao.supprimerFavoriParIsbn(isbn) }
    }

    @Test
    fun `étant donné un Modèle, lorsqu'on appelle obtenirLivreFavori, on obtient un livre favori`() = runTest {
        val favoriMocké = Favoris("ISBN1", "Titre1", "Auteur1", "Image1")
        coEvery { mockFavorisDao.obtenirFavoriParIsbn("ISBN1") } returns favoriMocké

        val résultat = modèle.obtenirLivreFavori("ISBN1")

        assertEquals(favoriMocké, résultat)
        coVerify { mockFavorisDao.obtenirFavoriParIsbn("ISBN1") }
    }
}
