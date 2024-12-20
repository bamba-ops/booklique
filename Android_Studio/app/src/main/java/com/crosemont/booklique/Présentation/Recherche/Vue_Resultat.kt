package com.crosemont.booklique.Présentation.Recherche

import Livre
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.Présentation.Favoris.Présentateur
import com.crosemont.booklique.R
import com.squareup.picasso.Picasso

class Vue_Resultat : Fragment() {

    lateinit var resultatRechercheConteneur: LinearLayout
    lateinit var textRechercheParDefaut: TextView
    private lateinit var présentateur: Présentateur_Resultat
    lateinit var affichageDefilementResultatRecherche: ScrollView
    private lateinit var chargement: ProgressBar
    private lateinit var text_critere_recherche: TextView
    private var favorisList: MutableList<ImageView> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_resultats_recherche, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultatRechercheConteneur = view.findViewById(R.id.resultat_de_recherche_conteneur)
        affichageDefilementResultatRecherche = view.findViewById(R.id.affichage_defilement_resultat_de_recherche)
        textRechercheParDefaut = view.findViewById(R.id.text_recherche_par_defaut)
        text_critere_recherche = view.findViewById(R.id.text_critere_recherche)
        chargement = view.findViewById(R.id.chargement)
        présentateur = Présentateur_Resultat(this)


        présentateur.traiter_livre()

    }

    fun changer_image_resource_true(index: Int ){
        favorisList[index].setImageResource(R.drawable.favoris_true)
    }

    fun changer_image_resource_false(index: Int){
        favorisList[index].setImageResource(R.drawable.favoris_false)
    }

    fun ajouter_favoris_list(favoris: ImageView){
        this.favorisList.add(favoris)
    }

    fun afficherLivres(livre: Livre, index: Int){
        val inflater = layoutInflater
        textRechercheParDefaut.text = "Aucun résultat trouvé."
        val livreView = inflater.inflate(
            R.layout.fragment_article_livre,
            resultatRechercheConteneur,
            false
        )

        val imageView = livreView.findViewById<ImageView>(R.id.livre_image)
        val titreTextView = livreView.findViewById<TextView>(R.id.livre_titre)
        val auteurTextView = livreView.findViewById<TextView>(R.id.livre_auteur)
        val genreTextView = livreView.findViewById<TextView>(R.id.livre_genre)
        val favoris = livreView.findViewById<ImageView>(R.id.icone_favoris)

        favoris.tag = index
        ajouter_favoris_list(favoris)

        titreTextView.text = livre.titre
        auteurTextView.text = livre.auteur
        genreTextView.text = livre.genre
        présentateur.traiter_livre_favori(livre.isbn, favoris.tag as Int)

        Picasso.get()
            .load(livre.image_url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(imageView)

        favoris.setOnClickListener {
            présentateur.traiter_livre_favori_boutton(livre, favoris.tag as Int)
        }

        livreView.setOnClickListener {
            présentateur.traiter_obtenir_livre(livre.isbn)
        }

        resultatRechercheConteneur.addView(livreView)

    }

    fun afficherChargement(){
        chargement.visibility = View.VISIBLE

    }

    fun enleverChargement(){
        chargement.visibility = View.GONE
    }

    fun supprimerResultatRechercheConteneur(){
        resultatRechercheConteneur.removeAllViews()
    }

    fun afficherTextParDefaut(){
        textRechercheParDefaut.visibility = View.VISIBLE
    }

    fun enleverTextParDefaut(){
        textRechercheParDefaut.visibility = View.GONE
    }

    fun afficherDefilementResultatRecherche(){
        affichageDefilementResultatRecherche.visibility = View.VISIBLE
    }

    fun enleverDefilementResultatRecherche(){
        affichageDefilementResultatRecherche.visibility = View.GONE
    }

    fun afficherTextCritereRecherche(){
        text_critere_recherche.visibility = View.VISIBLE
    }

    fun enleverTextCritereRecherche(){
        text_critere_recherche.visibility = View.GONE
    }

    fun modifierTextCritereRecherche(text: String){
        text_critere_recherche.text = text
    }

    fun modifierTextRechercheParDefaut(text: String){
        textRechercheParDefaut.text = text
    }

    fun chargerTextRechercheParDefaut(): Boolean {
        return textRechercheParDefaut.visibility == View.GONE
    }

     fun naviguerVersDetailLivre() {
         findNavController().navigate(R.id.action_resultat_to_detail_livre)
    }

    fun chargerDefilementResultatRecherche(): Boolean{
        return affichageDefilementResultatRecherche.visibility == View.GONE
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