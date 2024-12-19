package com.crosemont.booklique.Présentation.Favoris

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R
import com.crosemont.booklique.domaine.entité.Favoris
import com.squareup.picasso.Picasso


class Vue : Fragment() {

    private lateinit var resultatLivresFavoris: LinearLayout
    private lateinit var présentateur: Présentateur
    private lateinit var  textView: TextView
    private var iconeFavorisList: MutableList<ImageView> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoris, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultatLivresFavoris = view.findViewById(R.id.resultat_livres_favoris)
        textView = view.findViewById(R.id.rienFavoris)
        présentateur = Présentateur(this)
        présentateur.chargerLivresFavoris()


    }

    fun charger_affichage_livre_favoris(){
        resultatLivresFavoris.removeAllViews()
    }

    fun afficher_text_view(){
        textView.visibility = View.VISIBLE
    }

    fun enlever_text_view(){
        textView.visibility = View.GONE
    }

    fun changer_resource_iconeFavoris_true(iconeFavoris: ImageView){
        iconeFavoris.setImageResource(R.drawable.favoris_true)
    }

    fun changer_resource_iconeFavoris_false(index: Int){
        iconeFavorisList[index].setImageResource(R.drawable.favoris_false)
    }

    fun ajout_iconeFavoris_list(iconeFavoris: ImageView){
        this.iconeFavorisList.add(iconeFavoris)
    }

    fun afficherLivresFavoris(favoris: Favoris, index: Int) {
        val inflater = layoutInflater

        val livresFavorisView = inflater.inflate(
            R.layout.fragment_article_livre,
            resultatLivresFavoris,
            false
        )

        val imageView = livresFavorisView.findViewById<ImageView>(R.id.livre_image)
        val titreTextView = livresFavorisView.findViewById<TextView>(R.id.livre_titre)
        val auteurTextView = livresFavorisView.findViewById<TextView>(R.id.livre_auteur)
        val genreTextView = livresFavorisView.findViewById<TextView>(R.id.livre_genre)
        val iconeFavoris = livresFavorisView.findViewById<ImageView>(R.id.icone_favoris)

            titreTextView.text = favoris.titre
            auteurTextView.text = favoris.auteur
            genreTextView.text = favoris.genre
            iconeFavoris.setImageResource(R.drawable.favoris_true)
            iconeFavoris.tag = index

            ajout_iconeFavoris_list(iconeFavoris)

        Picasso.get()
            .load(favoris.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageView)

            livresFavorisView.setOnClickListener {
                présentateur.traiter_obtenir_livre(favoris.isbn)
                findNavController().navigate(R.id.action_favoris_to_detail_livre)
            }

            iconeFavoris.setOnClickListener {
                présentateur.traiter_favoris(favoris, iconeFavoris.tag as Int)
            }

            resultatLivresFavoris.addView(livresFavorisView)
        }

        iconeFavoris.setOnClickListener {
            présentateur.traiter_favoris(favoris, iconeFavoris)
        }

        resultatLivresFavoris.addView(livresFavorisView)
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