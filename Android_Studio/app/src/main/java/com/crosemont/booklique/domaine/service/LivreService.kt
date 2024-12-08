package com.crosemont.booklique.domaine.service
import Livre
import com.crosemont.booklique.sourcededonnées.SourceDeDonnéesLivreHTTP

class LivreService{

    companion object{

        private val source_api = SourceDeDonnéesLivreHTTP()
        var _obtenirLivreParAuteur: String? = null
        var _obtenirLivreParNouveaute: String? = null
        var _obtenirLivresParGenre: String? = null
        var _obtenirLivre: String? = null
        var _obtenirLivreParTitre: String? = null
        var isObtenirLivreParNouveaute: Boolean = false
        var isObtenirLivreParAuteur: Boolean = false
        var isObtenirLivresParNouveautes: Boolean = false
        var isObtenirLivresParGenre: Boolean = false
        var isObtenirLivre: Boolean = false
        var isObtenirLivreParTitre: Boolean = false

        fun definirLivreParAuteur(auteur: String?){
            _obtenirLivreParAuteur = auteur
            isObtenirLivreParAuteur = true
        }

        fun definirLivreParTitre(titre: String?){
            _obtenirLivreParTitre = titre
            isObtenirLivreParTitre = true
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

        fun obtenirLivreParISBN(isbn: String): Livre? {
            return source_api.obtenirLivreParIsbn(isbn)
        }

        fun obtenirLivre(isbn: String): Livre? {
            return obtenirLivreParISBN(isbn)
        }

        fun obtenirTousLivres(): List<Livre> {
            return source_api.obtenirTousLivres()
        }

        fun obtenirLivresParTires(): List<String>{
            return source_api.obtenirTousLivres().map { it.titre }.distinct()
        }

        fun obtenirLivresParAuteursListString(): List<String>{
            return source_api.obtenirTousLivres().map { it.auteur }.distinct()
        }

        fun obtenirLivresParAuteur(auteur: String): List<Livre>{
            return source_api.obtenirTousLivres().filter { it.auteur == auteur }
        }

        fun obtenirLivreParTitre(titre: String): Livre? {
            return source_api.obtenirTousLivres().find { it.titre == titre }
        }

        fun obtenirLivresParGenre(genre: String?): List<Livre>{
            return source_api.obtenirTousLivres().filter { it.genre == genre }
        }

        fun obtenirLivresParNouveautesPrend10(): List<Livre> {
            return source_api.obtenirTousLivres()
                .sortedByDescending { it.date_publication }
                .take(10)
        }

        fun obtenirLivresParNouveautesPrend5(): List<Livre> {
            return source_api.obtenirTousLivres()
                .sortedByDescending { it.date_publication }
                .take(5)
        }

        fun obtenirLivresParAuteur(): List<Livre>{
            return source_api.obtenirTousLivres().groupBy { it.auteur }.mapNotNull { (_, livresParAuteur) -> livresParAuteur.firstOrNull()}.take(5)
        }

        fun modifierLivreParIsbn(isbn: String, livre: Livre): Livre?{
            return source_api.modifierLivreParIsbn(isbn, livre)
        }
    }


}