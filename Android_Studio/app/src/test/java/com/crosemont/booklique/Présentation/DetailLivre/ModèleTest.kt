package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import android.content.Context
import androidx.room.Room
import com.crosemont.booklique.domaine.entité.Favoris
import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.entité.ReservationHistorique
import com.crosemont.booklique.domaine.service.LivreService
import com.crosemont.booklique.domaine.service.ReservationService
import com.crosemont.booklique.sourcededonnées.dao.FavorisDao
import com.crosemont.booklique.sourcededonnées.dao.ReservationHistoriqueDao
import com.crosemont.booklique.sourcededonnées.dao.dbConfig.AppDatabase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ModèleTest {

    private lateinit var mockContext: Context
    private lateinit var mockDatabase: AppDatabase
    private lateinit var mockFavorisDao: FavorisDao
    private lateinit var mockReservationDao: ReservationHistoriqueDao
    private lateinit var modèle: Modèle

    @Before
    fun setUp() {
        mockContext = mockk(relaxed = true)
        mockDatabase = mockk(relaxed = true)
        mockFavorisDao = mockk(relaxed = true)
        mockReservationDao = mockk(relaxed = true)

        mockkStatic(Room::class)
        every { Room.inMemoryDatabaseBuilder(mockContext, AppDatabase::class.java).allowMainThreadQueries().build() } returns mockDatabase
        every { mockDatabase.favorisDao() } returns mockFavorisDao
        every { mockDatabase.reservationHistoriqueDao() } returns mockReservationDao

        modèle = Modèle(mockContext)
    }

    @Test
    fun `test modifierLivreParIsbn returns expected Livre`() {
        val isbn = "123456"
        val livre = Livre(
            isbn = isbn,
            image_url = "URL",
            titre = "Livre Modifié",
            description = "Description",
            auteur = "Auteur",
            editeur = "Editeur",
            genre = "Genre",
            date_publication = java.util.Date(),
            nombre_pages = 200,
            quantite = 10
        )

        mockkObject(LivreService)
        every { LivreService.modifierLivreParIsbn(isbn, any()) } returns livre

        val result = modèle.modifierLivreParIsbn(isbn, livre)

        assertEquals(livre, result)
        verify { LivreService.modifierLivreParIsbn(isbn, livre) }
    }

    @Test
    fun `test ajouterReservation calls ReservationService`() {
        val reservation = Reservation(id = 1, debut = java.util.Date(), termine = java.util.Date(), livreIsbn = "123456")

        mockkObject(ReservationService)
        every { ReservationService.ajouterReservation(reservation) } returns reservation

        val result = modèle.ajouterReservation(reservation)

        assertEquals(reservation, result)
        verify { ReservationService.ajouterReservation(reservation) }
    }



    @Test
    fun `test estDisponible returns correct Boolean`() {
        assertEquals(true, modèle.estDisponible("Disponible"))
        assertEquals(false, modèle.estDisponible("Indisponible"))
    }

    @Test
    fun `test obtenirLivre returns expected Livre`() {
        val isbn = "123456"
        val livre = Livre(
            isbn = isbn,
            image_url = "URL",
            titre = "Livre Disponible",
            description = "Description",
            auteur = "Auteur",
            editeur = "Editeur",
            genre = "Genre",
            date_publication = java.util.Date(),
            nombre_pages = 300,
            quantite = 5
        )

        mockkObject(LivreService)
        every { LivreService.isObtenirLivre } returns true
        every { LivreService._obtenirLivre } returns isbn
        every { LivreService.obtenirLivre(isbn) } returns livre

        val result = modèle.obtenirLivre()

        assertEquals(livre, result)
        verify { LivreService.obtenirLivre(isbn) }
    }
}
