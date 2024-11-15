package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import com.crosemont.booklique.domaine.mork_data.Data

class Modèle {

    fun obtenirLivre(): Livre?{
        if(Data.isObtenirLivre){
            return Data.obtenirLivre(Data._obtenirLivre!!)
        }
        return null
    }

}