package com.crosemont.booklique.Présentation

import Livre
import com.crosemont.booklique.Présentation.Recherche.Modèle
import com.crosemont.booklique.Présentation.Recherche.Présentateur
import com.crosemont.booklique.Présentation.Recherche.Vue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class PrésentateurTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var mockVue: Vue
    private lateinit var mockModèle: Modèle
    private lateinit var présentateur: Présentateur

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        mockVue = mock(Vue::class.java)
        mockModèle = mock(Modèle::class.java)
        présentateur = Présentateur(mockVue)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `étant donné un utilisateur connecté, lorsque l'historique est obtenu, les suggestions sont mises à jour`() = runTest {
        `when`(mockVue.connexion()).thenReturn(true)
        `when`(mockModèle.obtenirHistoriqueRecherches()).thenReturn(listOf("Recherche 1", "Recherche 2"))

        présentateur.traiter_historique_recherche()

        verify(mockVue).mettre_a_jour_suggestion(listOf(" Recherche 1", " Recherche 2"))
    }

    @Test
    fun `étant donné un utilisateur non connecté, lorsque l'historique est demandé, un dialogue de connexion s'affiche`() = runTest {
        `when`(mockVue.connexion()).thenReturn(false)

        présentateur.traiter_historique_recherche()

        verify(mockVue).afficherDialogueConnexion()
        verifyNoInteractions(mockModèle)
    }

    @Test
    fun `étant donné un utilisateur connecté, lorsque l'historique est supprimé, les suggestions sont vidées`() = runTest {
        `when`(mockVue.connexion()).thenReturn(true)

        présentateur.traiter_supprimer_recherche_historique()

        verify(mockModèle).supprimerHistoriqueRecherche()
        verify(mockVue).mettre_a_jour_suggestion(emptyList())
    }

    @Test
    fun `étant donné un utilisateur connecté, lorsque des suggestions sont mises à jour par titre, elles incluent l'historique et les résultats`() = runTest {
        `when`(mockVue.connexion()).thenReturn(true)
        `when`(mockVue.charger_radio_id()).thenReturn(1)
        `when`(mockModèle.obtenirHistoriqueRecherches()).thenReturn(listOf("Recherche 1"))
        `when`(mockModèle.obtenirLivresParTitres()).thenReturn(listOf("Titre 1", "Titre 2"))

        présentateur.traiter_mise_a_jour_suggestions(1)

        verify(mockVue).mettre_a_jour_suggestion(
            listOf("Recherche 1", "Titre 1", "Titre 2")
        )
    }

    @Test
    fun `étant donné un utilisateur connecté, lorsque des suggestions sont mises à jour par auteur, elles incluent l'historique et les résultats`() = runTest {
        `when`(mockVue.connexion()).thenReturn(true)
        `when`(mockVue.charger_radio_id()).thenReturn(2)
        `when`(mockModèle.obtenirHistoriqueRecherches()).thenReturn(listOf("Recherche 2"))
        `when`(mockModèle.obtenirLivresParAuteursListString()).thenReturn(listOf("Auteur 1", "Auteur 2"))

        présentateur.traiter_mise_a_jour_suggestions(2)

        verify(mockVue).mettre_a_jour_suggestion(
            listOf("Recherche 2", "Auteur 1", "Auteur 2")
        )
    }

    @Test
    fun `étant donné un utilisateur connecté, lorsque la recherche est lancée, le texte est mis à jour et les résultats navigués`() = runTest {
        `when`(mockVue.connexion()).thenReturn(true)
        `when`(mockVue.charger_barre_recherche(0)).thenReturn("Recherche Test")
        `when`(mockVue.charger_group_radio_checked_id()).thenReturn(1)
        `when`(mockVue.charger_radio_id()).thenReturn(1)

        présentateur.traiter_lancer_recherche(0)

        verify(mockModèle).ajouterRecherche("Recherche Test")
        verify(mockVue).charger_text_barre_recherche("Recherche Test")
        verify(mockVue).naviguer_resultat()
    }

    @Test
    fun `étant donné un utilisateur non connecté, lorsque la recherche est lancée, un dialogue de connexion s'affiche`() = runTest {
        `when`(mockVue.connexion()).thenReturn(false)

        présentateur.traiter_lancer_recherche(0)

        verify(mockVue).afficherDialogueConnexion()
        verifyNoInteractions(mockModèle)
    }
}
