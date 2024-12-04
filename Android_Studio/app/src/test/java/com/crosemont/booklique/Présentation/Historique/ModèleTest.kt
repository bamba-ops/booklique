package com.crosemont.booklique.Présentation.Historique

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlin.test.Test

class ModèleTest{

    @Test
    fun `étant donné un modèle de réservation, lorsqu'on accède aux propriétés, elles doivent être correctement initialisées`() {
        // Données de test
        val titre = "Les secrets de la forêt"
        val dateReservation = "20/03/2024"
        val dateRetour = "10/04/2024"

        // Initialisation du modèle
        val modèle = Modèle(titre, dateReservation, dateRetour)

        // Vérification que les propriétés sont correctement initialisées
        assertEquals(titre, modèle.titre)
        assertEquals(dateReservation, modèle.dateReservation)
        assertEquals(dateRetour, modèle.dateRetour)
    }

    @Test
    fun `étant donné un modèle de réservation, lorsqu'on accède aux propriétés, les valeurs doivent être les bonnes`() {
        // Création d'un modèle avec des valeurs
        val modèle = Modèle("Le voyage du temps", "15/09/2024", "05/10/2024")

        // Vérification des valeurs des propriétés
        assertNotNull(modèle.titre)
        assertEquals("Le voyage du temps", modèle.titre)

        assertNotNull(modèle.dateReservation)
        assertEquals("15/09/2024", modèle.dateReservation)

        assertNotNull(modèle.dateRetour)
        assertEquals("05/10/2024", modèle.dateRetour)
    }

    @Test
    fun `étant donné un modèle avec des dates invalides, lorsqu'on vérifie, les valeurs doivent être nulles ou invalides`() {
        // Création d'un modèle avec des dates invalides
        val modèle = Modèle("Book Titre", "", "")

        // Vérification des valeurs invalides
        assertEquals("", modèle.dateReservation)
        assertEquals("", modèle.dateRetour)
    }
}