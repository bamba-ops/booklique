package com.crosemont.booklique.Présentation.Historique

import com.crosemont.booklique.domaine.entité.Reservation
import com.crosemont.booklique.domaine.mork_data.Data
import junit.framework.TestCase.assertEquals
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.text.SimpleDateFormat
import kotlin.test.Test


class PrésentateurTest{

    private val vueMock = mock(Vue::class.java)
    private val présentateur = Présentateur(vueMock)

    @Test
    fun `étant donné une date valide, lorsqu'on la formate, alors on obtient une chaîne au format dd-MM-yyyy`() {
        val date = SimpleDateFormat("yyyy-MM-dd").parse("2024-03-20")
        val resultat = présentateur.formaterDateHistorique(date)

        assertEquals("20/03/2024", resultat)
    }

    @Test
    fun `étant donné une date nulle, lorsqu'on la formate, alors on obtient «Date invalide»`() {
        val resultat = présentateur.formaterDateHistorique(null)

        assertEquals("Date invalide", resultat)
    }

    @Test
    fun `étant donné une liste de réservations, lorsqu'on appelle afficherHistoriqueReservation, alors la vue reçoit les données formatées`() {
        // Péparation des données
        val réservations = listOf(
            Reservation(
                id = 1,
                debut = SimpleDateFormat("yyyy-MM-dd").parse("2024-03-20"),
                termine = SimpleDateFormat("yyyy-MM-dd").parse("2024-04-10"),
                livre = Data.obtenirLivresDemo()[0]
            ),
            Reservation(
                id = 2,
                debut = SimpleDateFormat("yyyy-MM-dd").parse("2024-05-05"),
                termine = SimpleDateFormat("yyyy-MM-dd").parse("2024-05-25"),
                livre = Data.obtenirLivresDemo()[1]
            )
        )

        `when`(Data.obtenirReservationsDemo()).thenReturn(réservations)

        val modèlesAttendus = listOf(
            Modèle(
                titre = "Les secrets de la forêt",
                dateReservation = "20/03/2024",
                dateRetour = "10/04/2024"
            ),
            Modèle(
                titre = "Mystères sous la mer",
                dateReservation = "05/05/2024",
                dateRetour = "25/05/2024"
            )
        )

        // Action
        présentateur.afficherHistoriqueReservation()

        // Vérification
        verify(vueMock).afficherHistoriqueReservation(modèlesAttendus)
    }

    @Test
    fun `étant donné une liste vide de réservations, lorsqu'on appelle afficherHistoriqueReservation, alors la vue reçoit une liste vide`() {
        // Préparation
        `when`(Data.obtenirReservationsDemo()).thenReturn(emptyList())

        // Action
        présentateur.afficherHistoriqueReservation()

        //Vérification
        verify(vueMock).afficherHistoriqueReservation(emptyList())
    }

    @Test
    fun `étant donné une réservation avec des dates non formatées, lorsqu'on appelle afficherHistoriqueReservation, alors les dates sont correctement formatées`() {
        // Préparation
        val réservations = listOf(
            Reservation(
                id = 3,
                debut = SimpleDateFormat("yyyy-MM-dd").parse("2024-07-12"),
                termine = SimpleDateFormat("yyyy-MM-dd").parse("2024-08-01"),
                livre = Data.obtenirLivresDemo()[2]
            )
        )

        `when`(Data.obtenirReservationsDemo()).thenReturn(réservations)

        val modèlesAttendus = listOf(
            Modèle(
                titre = "La magie des étoiles",
                dateReservation = "12/07/2024",
                dateRetour = "01/08/2024"
            )
        )

        // Action
        présentateur.afficherHistoriqueReservation()

        // Vérification
        verify(vueMock).afficherHistoriqueReservation(modèlesAttendus)
    }
}