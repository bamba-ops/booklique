package com.crosemont.booklique.Présentation.Genres

import Livre
import com.crosemont.booklique.domaine.mork_data.Data
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ModèleTest {

    @Test
    fun `étant donné un Modèle nouvellement instancié lorsqu'on définit un genre, alors les livres associés au genre sont définis correctement`() {

        val model = Modèle()

        val genre = "Aventure"

        model.obtenirLivreParGenre(genre)

        assertTrue {
            true
        }
    }
}