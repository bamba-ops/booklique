package com.crosemont.booklique.domaine.mork_data

import Livre
import com.crosemont.booklique.domaine.entité.Reservation
import java.util.Date

class Data {

    companion object {
        private val livresFavoris = mutableListOf<Livre>()

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
                )
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

