package com.crosemont.booklique.Présentation.Accueil

import Livre
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso
import java.lang.Exception

class Vue : Fragment() {

    lateinit var sectionGenres: LinearLayout
    lateinit var sectionNouveautes: LinearLayout
    lateinit var carteAuteur: LinearLayout
    lateinit var listeNouveautes: LinearLayout
    lateinit var présentateur: Présentateur
    lateinit var chargement: FrameLayout
    lateinit var accueil: ScrollView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accueil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sectionGenres = view.findViewById(R.id.section_genres)
        sectionNouveautes = view.findViewById(R.id.section_nouveautes)
        listeNouveautes = view.findViewById(R.id.liste_nouveautes)
        carteAuteur = view.findViewById(R.id.carte_auteurs)
        chargement = view.findViewById(R.id.chargement)
        accueil = view.findViewById(R.id.accueil)

        présentateur = Présentateur(this, modèle = Modèle())

        présentateur.traiter_affichage_livre()

        sectionGenres.setOnClickListener {
            présentateur.traiter_naviguer_genres()
        }

        sectionNouveautes.setOnClickListener {
            présentateur.traiter_obtenir_livres_par_nouveautes()
        }


    }

    fun afficher_livre_par_nouveautes(livre: Livre) {
            val listeNouveautesView = LayoutInflater.from(context).inflate(R.layout.fragment_section_nouveautes, listeNouveautes, false)

            val imageNouveaute: ImageView = listeNouveautesView.findViewById(R.id.image_nouveaute)
            val chargementNouveaute: ProgressBar = listeNouveautesView.findViewById(R.id.chargementNouveaute)


        Picasso.get()
                .load(livre.image_url)
                .into(imageNouveaute, object: com.squareup.picasso.Callback{
                    override fun onSuccess() {
                        chargementNouveaute.visibility = View.GONE
                        imageNouveaute.visibility = View.VISIBLE
                    }

                    override fun onError(e: Exception?) {
                        chargementNouveaute.visibility = View.GONE
                        imageNouveaute.setImageResource(R.drawable.error_image)
                        imageNouveaute.visibility = View.VISIBLE
                    }
                })

            imageNouveaute.setOnClickListener {
                présentateur.traiter_obtenir_livre_par_nouveaute(livre.isbn)
            }


            listeNouveautes.addView(listeNouveautesView)


    }

    fun afficher_livre_par_auteur(livre: Livre){
            val carteAuteurView = LayoutInflater.from(context).inflate(R.layout.fragment_carte_auteur, carteAuteur, false)

            val nomAuteur: TextView = carteAuteurView.findViewById(R.id.nom_auteur)
            val imageAuteur: ImageView = carteAuteurView.findViewById(R.id.image_auteur)
            val btnCarteAuteur: LinearLayout = carteAuteurView.findViewById(R.id.carte_auteur)
            val chargementAuteur: ProgressBar = carteAuteurView.findViewById(R.id.chargement)

            nomAuteur.text = livre.auteur
            Picasso.get()
                .load(livre.image_url)
                .into(imageAuteur, object: com.squareup.picasso.Callback{
                    override fun onSuccess() {
                        chargementAuteur.visibility = View.GONE
                        imageAuteur.visibility = View.VISIBLE
                    }

                    override fun onError(e: Exception?) {
                        chargementAuteur.visibility = View.GONE
                        imageAuteur.setImageResource(R.drawable.error_image)
                        imageAuteur.visibility = View.VISIBLE
                    }
                })

            btnCarteAuteur.setOnClickListener{
                présentateur.traiter_obtenir_livres_par_auteur(livre.auteur)
            }

            carteAuteur.addView(carteAuteurView)

    }

    fun afficherChargement(){
        chargement.visibility = View.VISIBLE
    }

    fun afficherAccueil(){
        accueil.visibility = View.VISIBLE
    }

    fun enleverAccueil(){
        accueil.visibility = View.GONE
    }

    fun naviguerVersGenres() {
        findNavController().navigate(R.id.action_accueil_to_genres)
    }

     fun naviguerVersResultatsAvecAuteur() {
        findNavController().navigate(R.id.action_accueil_to_resultats)
    }

     fun naviguerVersResultatsAvecNouveautes() {
        findNavController().navigate(R.id.action_accueil_to_resultats)
    }

     fun naviguerVersDetailLivre(isbn: String) {
        findNavController().navigate(R.id.action_accueil_to_detail_livre)
    }

    fun afficherDialogueConnexion(){
        AlertDialog.Builder(requireContext())
            .setTitle("Connexion internet perdue")
            .setMessage("Veuillez vous reconnecter")
            .setNegativeButton("OK"){
                    dialog, which -> dialog.dismiss()
            }.show()

    }
    @SuppressLint("ServiceCast")
    fun connexion() : Boolean{
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}