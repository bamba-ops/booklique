package com.crosemont.booklique.Présentation.Historique

import Livre
import android.content.Context
import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.entité.ReservationHistorique
import com.crosemont.booklique.domaine.service.LivreService
import com.crosemont.booklique.domaine.service.ReservationService
import com.crosemont.booklique.sourcededonnées.dao.ReservationHistoriqueDao
import com.crosemont.booklique.sourcededonnées.dao.dbConfig.AppDatabase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class ModèleTest {

    private lateinit var mockContext: Context
    private lateinit var mockDatabase: AppDatabase
    private lateinit var mockReservationHistoriqueDao: ReservationHistoriqueDao
    private lateinit var modèle: Modèle
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockContext = mockk(relaxed = true)
        mockDatabase = mockk(relaxed = true)
        mockReservationHistoriqueDao = mockk(relaxed = true)

        mockkStatic(androidx.room.Room::class)
        every { androidx.room.Room.databaseBuilder(mockContext, AppDatabase::class.java, "app_database").build() } returns mockDatabase
        every { mockDatabase.reservationHistoriqueDao() } returns mockReservationHistoriqueDao

        modèle = Modèle(mockContext)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }



    @Test
    fun `test obtenirLivreParIsbn returns expected Livre`() {
        val isbn = "123456"
        val livre = Livre(
            isbn = isbn,
            image_url = "URL",
            titre = "Livre Historique",
            description = "Description",
            auteur = "Auteur",
            editeur = "Editeur",
            genre = "Genre",
            date_publication = java.util.Date(),
            nombre_pages = 300,
            quantite = 5
        )

        mockkObject(LivreService)
        every { LivreService.obtenirLivreParISBN(isbn) } returns livre

        val result = modèle.obtenirLivreParIsbn(isbn)

        assertEquals(livre, result)
        verify { LivreService.obtenirLivreParISBN(isbn) }
    }

    @Test
    fun `test obtenirReservation returns expected list`() {
        val reservationList = listOf(
            Reservation(id = 1, debut = java.util.Date(), termine = java.util.Date(), livreIsbn = "123456"),
            Reservation(id = 2, debut = java.util.Date(), termine = java.util.Date(), livreIsbn = "654321")
        )

        mockkObject(ReservationService)
        every { ReservationService.obtenirReservations() } returns reservationList

        val result = modèle.obtenirReservation()

        assertEquals(reservationList, result)
        verify { ReservationService.obtenirReservations() }
    }
}
