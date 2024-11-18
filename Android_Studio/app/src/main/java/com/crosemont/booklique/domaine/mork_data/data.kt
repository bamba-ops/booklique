package com.crosemont.booklique.domaine.mork_data

import Livre
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crosemont.booklique.domaine.entité.Reservation
import java.util.Calendar
import java.util.Date

class Data {

    companion object {
        private val livresFavoris = mutableListOf<Livre>()
        var _obtenirLivreParAuteur: String? = null
        var _obtenirLivreParNouveaute: String? = null
        var _obtenirLivresParGenre: String? = null
        var _obtenirLivre: String? = null
        var isObtenirLivreParNouveaute: Boolean = false
        var isObtenirLivreParAuteur: Boolean = false
        var isObtenirLivresParNouveautes: Boolean = false
        var isObtenirLivresParGenre: Boolean = false
        var isObtenirLivre: Boolean = false

        fun definirLivreParAuteur(auteur: String?){
            _obtenirLivreParAuteur = auteur
            isObtenirLivreParAuteur = true
        }

        fun definirLivre(isbn: String?){
            _obtenirLivre = isbn
            isObtenirLivre = true
        }

        fun definirLivreParGenre(genre: String?){
            _obtenirLivresParGenre = genre
            isObtenirLivresParGenre = true
        }

        fun definirLivreParNouveaute(isbn: String?){
            _obtenirLivreParNouveaute = isbn
            isObtenirLivreParNouveaute = true
        }

        fun definirLivresParNouveautes(){
            isObtenirLivresParNouveautes = true
        }

        fun obtenirLivresParNouveautes(): List<Livre>{
            return obtenirLivresDemo().sortedByDescending { it.date_publication }.take(5)
        }

        fun obtenirLivre(isbn: String): Livre? {
            return obtenirLivreParISBN(isbn)
        }

        fun obtenirLivresParAuteur(auteur: String): List<Livre>{
            return obtenirLivresDemo().filter { it.auteur == auteur }
        }

        fun obtenirLivresParAuteurs(): List<Livre>{
            return obtenirLivresDemo().groupBy { it.auteur }.mapNotNull { (_, livresParAuteur) -> livresParAuteur.firstOrNull()}
        }

        fun ajouterLivreFavori(livre: Livre){
            livresFavoris.add(livre)
        }

        fun retirerLivreFavoriParISBN(isbn: String){
            livresFavoris.removeIf { it.isbn == isbn }
        }

        fun obtenirLivresFavoris(): List<Livre>{
            return livresFavoris
        }

        fun estLivreFavori(isbn: String): Boolean {
            return livresFavoris.any { it.isbn == isbn }
        }

        fun obtenirLivreParISBN(isbn: String): Livre? {
            return obtenirLivresDemo().find { it.isbn == isbn }
        }

        fun obtenirLivreFavorisParISBN(isbn: String): Livre? {
            return obtenirLivresFavoris().find { it.isbn == isbn }
        }

        fun obtenirLivresParGenre(genre: String?): List<Livre>{
            return obtenirLivresDemo().filter { it.genre == genre }
        }

        fun obtenirLivresDemo(): List<Livre> {
            return listOf(
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
                ),
                Livre(
                    isbn = "978-3-16-148413-1",
                    image_url = "https://i.imghippo.com/files/Uvff8490Ak.png",
                    titre = "Le voyage du temps",
                    description = "Une exploration fascinante des époques perdues.",
                    auteur = "Harper Lee",
                    editeur = "Éditions Temps",
                    genre = "Histoire",
                    date_publication = Date(122, 0, 15), // 2022-01-15
                    nombre_pages = 360,
                    quantite = 4
                ),
                Livre(
                    isbn = "978-3-16-148417-9",
                    image_url = "https://i.imghippo.com/files/Os4847ZDI.jpeg",
                    titre = "Tree Fellas: Olly Oak",
                    description = "Dans un petit village isolé, une jeune archiviste découvre un journal ancien caché dans les murs d'une vieille maison. Les pages, mystérieusement liées à des événements du présent, révèlent une histoire d'amour impossible, des secrets de famille enfouis et une tragédie oubliée. Alors que le passé et le présent s'entrelacent, elle doit résoudre l'énigme avant que l'histoire ne se répète.\n" +
                            "\n" +
                            "Un récit captivant mêlant mystère, romance et voyages dans le temps.",
                    auteur = "Olivia Wilson",
                    editeur = "Éditions Temps",
                    genre = "Biographies",
                    date_publication = Date(123, 3, 12),
                    nombre_pages = 230,
                    quantite = 4
                ),
                Livre(
                    isbn = "978-1-84-916432-8",
                    image_url = "https://i.imghippo.com/files/g2841BS.jpeg",
                    titre = "yellow: a poetry collection",
                    description = "Au cœur d'une ville où les lumières ne s'éteignent jamais, une journaliste intrépide découvre l'existence d'un réseau clandestin qui manipule les souvenirs des citoyens. Alors qu'elle plonge dans une enquête périlleuse, elle réalise que son propre passé a été altéré. Avec l'aide d'un mystérieux informateur, elle tente de dénouer les fils d'une conspiration qui pourrait changer le cours de l'humanité.",
                    auteur = "Olivia Wilson",
                    editeur = "Éditions cosmos",
                    genre = "Histoire",
                    date_publication = Date(122, 4, 15),
                    nombre_pages = 159,
                    quantite = 6
                ),
                Livre(
                    isbn = "978-0-14-312779-6",
                    image_url = "https://i.imghippo.com/files/zRDt7335bn.jpeg",
                    titre = "you are a plant",
                    description = "Lorsqu'une biologiste marine, Clara, est envoyée sur une île isolée pour étudier un phénomène inexplicable—des chants mystérieux venant des profondeurs—elle découvre bien plus que ce qu'elle avait imaginé. Les habitants parlent de légendes oubliées, et l'océan semble garder jalousement ses secrets. Au fil de ses recherches, Clara doit affronter son propre passé et choisir entre la science et la magie, la réalité et l’imaginaire.",
                    auteur = "Olivia Wilson",
                    editeur = "Éditions Océan",
                    genre = "Afffaires",
                    date_publication = Date(122, 3, 12),
                    nombre_pages = 134,
                    quantite = 2
                ),

            )
        }

        fun obtenirReservationsDemo(): List<Reservation> {
            val livres = obtenirLivresDemo()
            return listOf(
                Reservation(
                    id = 1,
                    debut = Date(124, 2, 20), // 2024-03-20
                    termine = Date(124, 3, 10), // 2024-04-10
                    livre = livres[0]
                ),
                Reservation(
                    id = 2,
                    debut = Date(124, 4, 5), // 2024-05-05
                    termine = Date(124, 4, 25), // 2024-05-25
                    livre = livres[1]
                ),
                Reservation(
                    id = 3,
                    debut = Date(124, 6, 12), // 2024-07-12
                    termine = Date(124, 7, 1), // 2024-08-01
                    livre = livres[2]
                ),
                Reservation(
                    id = 4,
                    debut = Date(124, 8, 15), // 2024-09-15
                    termine = Date(124, 9, 5), // 2024-10-05
                    livre = livres[3]
                )
            )
        }
    }
}

