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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class présentateurTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var mockVue: Vue
    private lateinit var mockModèle: Modèle
    private lateinit var présentateur: Présentateur



    private val livreMock: List<Livre> = listOf(
        Livre(
            isbn = "978-3-16-148410-0",
            image_url = "https://i.imghippo.com/files/XF7523fok.png",
            titre = "Les secrets de la forêt",
            description = "Un voyage captivant au cœur de la nature sauvage.",
            auteur = "Olivia Wilson",
            editeur = "Éditions Nature",
            genre = "Afffaires",
            date_publication = Date(120, 4, 10), // 2020-05-10
            nombre_pages = 320,
            quantite = 0
        ),
        Livre(
            isbn = "978-3-16-148411-7",
            image_url = "https://i.imghippo.com/files/AP7294k.png",
            titre = "Mystères sous la mer",
            description = "Une enquête palpitante dans les profondeurs de l'océan.",
            auteur = "Lorna Alvarado",
            editeur = "Éditions Océan",
            genre = "Biographies",
            date_publication = Date(119, 2, 5), // 2019-03-05
            nombre_pages = 280,
            quantite = 0
        ),
        Livre(
            isbn = "978-3-16-148412-4",
            image_url = "https://i.imghippo.com/files/YFa6767kO.png",
            titre = "La magie des étoiles",
            description = "Un conte enchanteur sur les mystères de l'univers.",
            auteur = "Avery Davis",
            editeur = "Éditions Cosmos",
            genre = "Biographies",
            date_publication = Date(121, 10, 12), // 2021-11-12
            nombre_pages = 450,
            quantite = 2
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        mockVue = mock(Vue::class.java)
        mockModèle = mock(Modèle::class.java)
        présentateur = Présentateur(mockVue, mockModèle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `étant donné un Présentateur nouvellement instancié, lorsequ'on fait une recherche par auteur valide dont Olivia Wilson,  le livre correspondant s'affiche`() = runTest {
        val expectedLivre = listOf(livreMock[0])
        `when`(mockModèle.obtenirLivresParAuteur("Olivia Wilson")).thenReturn(expectedLivre)

        présentateur.afficherLivresParAuteur("Olivia Wilson")


        verify(mockVue).afficherLivres(expectedLivre[0])


    }

    @Test
    fun `étant donné un Présentateur nouvellement instancié, lorsequ'on fait une recherche par auteur invalide dont Auteur Inexistant, alors un message est affiché`() = runTest {
        `when`(mockModèle.obtenirLivresParAuteur("Auteur Inexistant")).thenReturn(emptyList())

        présentateur.afficherLivresParAuteur("Auteur Inexistant")

        verify(mockVue).modifierTextRechercheParDefaut("Aucun résultat trouvé pour 'Auteur Inexistant'.")
    }

    @Test
    fun `étant donné un Présentateur nouvellement instancié, lorsequ'on fait une recherche par titre valide dont Mystères sous la mer, le livre correspondant s'affiche`() = runTest {

        `when`(mockModèle.obtenirLivresParTitre("Mystères sous la mer")).thenReturn(livreMock)

        présentateur.afficherLivresParAuteur("Mystères sous la mer")

        verify(mockVue).afficherLivres(livreMock[1])
        verify(mockVue).afficherChargement(true)

    }

    @Test
    fun `étant donné un Présentateur nouvellement instancié, lorsequ'on fait une recherche par titre invalide dont un titre inexistant, alors un message est affiché`() = runTest {
        `when`(mockModèle.obtenirLivresParTitre("titre inexistant")).thenReturn(emptyList())

        présentateur.afficherLivresParTitre("titre inexistant")

        verify(mockVue).modifierTextRechercheParDefaut("Aucun résultat trouvé pour 'titre inexistant'.")

    }


    @Test
    fun `étant donné un Présentateur nouvellement instancié, lorsequ'on fait une recherche par titre ou auteur avec un critère inconnu dont 1, alors un message d'erreur est affiché`() = runTest {
        `when`(mockModèle.obtenirLivresParTitre("1")).thenReturn(emptyList())

        présentateur.afficherLivresParTitre("1")

        verify(mockVue).modifierTextRechercheParDefaut("Critère inconnu.")
        verify(mockVue).afficherDefilementResultatRecherche(false)

    }
}



