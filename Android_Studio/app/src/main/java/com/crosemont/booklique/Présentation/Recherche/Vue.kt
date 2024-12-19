package com.crosemont.booklique.Présentation.Recherche

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crosemont.booklique.R

class Vue : Fragment() {

    private lateinit var barreRecherche: AutoCompleteTextView
    private lateinit var groupeRadio: RadioGroup
    private lateinit var boutonRecherche: ImageButton
    private lateinit var présentateur: Présentateur
    private lateinit var boutonSupprimerHistorique: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recherche, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barreRecherche = view.findViewById(R.id.entree_recherche)
        groupeRadio = view.findViewById(R.id.radioGroup)
        boutonRecherche = view.findViewById(R.id.btnRecherche)
        boutonSupprimerHistorique = view.findViewById(R.id.btnSupprimerHistorique)
        présentateur = Présentateur(this)

        présentateur.traiter_mise_a_jour_suggestions("titre")
        présentateur.traiter_historique_recherche()

        boutonSupprimerHistorique.setOnClickListener {
            présentateur.traiter_supprimer_recherche_historique() // Appelle la méthode du présentateur pour supprimer l'historique
        }

        groupeRadio.setOnCheckedChangeListener { _, checkedId ->
            val critère = if (checkedId == R.id.radioTitre) "titre" else "auteur"
            présentateur.traiter_mise_a_jour_suggestions(critère)
        }

        barreRecherche.setOnItemClickListener { _, _, position, _ ->
            val suggestion = barreRecherche.adapter.getItem(position).toString()

            // Supprimer le préfixe avant de lancer la recherche
            val texteSansPrefixe = suggestion.removePrefix("🔍 ").removePrefix("⏳ ")
            barreRecherche.setText(texteSansPrefixe)

            // Lancer la recherche
            val critère = if (groupeRadio.checkedRadioButtonId == R.id.radioTitre) "titre" else "auteur"
            présentateur.lancerRecherche(texteSansPrefixe, critère)
        }



        // Action pour rechercher
        boutonRecherche.setOnClickListener {
            val rechercheTexte = barreRecherche.text.toString().trim()
            val critère = if (groupeRadio.checkedRadioButtonId == R.id.radioTitre) "titre" else "auteur"

            présentateur.lancerRecherche(rechercheTexte, critère)
        }

//        entreeRecherche.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
//                val rechercheTexte = entreeRecherche.text.toString()
//                présentateur.lancerRecherche(rechercheTexte)
//                true
//            } else {
//                false
//            }
//        }
//
//        // Action pour le bouton de recherche
//        btnRecherhce.setOnClickListener {
//            val rechercheTexte = entreeRecherche.text.toString().trim()
//            if (btnAuteur.isChecked) {
//                présentateur.afficherLivresParAuteur(rechercheTexte)
//            } else if (btnTitre.isChecked) {
//                présentateur.afficherLivresParTitre(rechercheTexte)
//            }
//            findNavController().navigate(R.id.action_recherche_to_resultat)
//        }


    }

    fun mettreAJourSuggestions(suggestions: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestions)
        barreRecherche.setAdapter(adapter)
        barreRecherche.threshold = 1 // Déclenche les suggestions après 1 caractère
    }

    fun naviguer_resultat(){
        findNavController().navigate(R.id.action_recherche_to_resultat)
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

