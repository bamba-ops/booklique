package com.crosemont.booklique.Présentation.DetailLivre

import Livre
import com.crosemont.booklique.domaine.mork_data.Data

class Modèle {

    fun obtenirLivreParNouveaute(): Livre?{
        if(Data.isObtenirLivreParNouveaute){
            return Data.obtenirLivreParNouveaute(Data._obtenirLivreParNouveaute!!)
        }
        return null
    }

}