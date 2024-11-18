package com.crosemont.booklique.domaine.service

import Livre
import com.crosemont.booklique.sourcededonnées.SourceBidon

class AccueilService(var source: SourceBidon = SourceBidon() ) {

    fun definirLivreParAuteur(auteur: String?){
        source._obtenirLivreParAuteur = auteur
        source.isObtenirLivreParAuteur = true
    }

    fun definirLivre(isbn: String?){
        source._obtenirLivre = isbn
        source.isObtenirLivre = true
    }

    fun definirLivreParGenre(genre: String?){
        source._obtenirLivresParGenre = genre
        source.isObtenirLivresParGenre = true
    }

    fun definirLivreParNouveaute(isbn: String?){
        source._obtenirLivreParNouveaute = isbn
        source.isObtenirLivreParNouveaute = true
    }

    fun definirLivresParNouveautes(){
        source.isObtenirLivresParNouveautes = true
    }

    fun obtenirLivresParNouveautes(): List<Livre>{
        return source.obtenirLivres().sortedByDescending { it.date_publication }
    }

    fun obtenirLivre(isbn: String): Livre? {
        return obtenirLivreParISBN(isbn)
    }

    fun obtenirLivresParAuteur(auteur: String): List<Livre>{
        return source.obtenirLivres().filter { it.auteur == auteur }
    }

    fun ajouterLivreFavori(livre: Livre){
        source.livresFavoris.add(livre)
    }

    fun retirerLivreFavoriParISBN(isbn: String){
        source.livresFavoris.removeIf { it.isbn == isbn }
    }

    fun obtenirLivresFavoris(): List<Livre>{
        return source.livresFavoris
    }

    fun estLivreFavori(isbn: String): Boolean {
        return source.livresFavoris.any { it.isbn == isbn }
    }

    fun obtenirLivreParISBN(isbn: String): Livre? {
        return source.obtenirLivres().find { it.isbn == isbn }
    }

    fun obtenirLivreFavorisParISBN(isbn: String): Livre? {
        return obtenirLivresFavoris().find { it.isbn == isbn }
    }

    fun obtenirLivresParGenre(genre: String?): List<Livre>{
        return source.obtenirLivres().filter { it.genre == genre }
    }

    fun obtenirLivres(): List<Livre>{
        return source.obtenirLivres()
    }
}