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
import android.widget.TextView
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
    private lateinit var radioTitre: TextView


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
        radioTitre = view.findViewById(R.id.radioTitre)
        boutonSupprimerHistorique = view.findViewById(R.id.btnSupprimerHistorique)
        présentateur = Présentateur(this)

        présentateur.traiter_mise_a_jour_suggestions(radioTitre.id)
        présentateur.traiter_historique_recherche()

        boutonSupprimerHistorique.setOnClickListener {
            présentateur.traiter_supprimer_recherche_historique()
        }

        groupeRadio.setOnCheckedChangeListener { _, checkedId ->
            présentateur.traiter_mise_a_jour_suggestions(checkedId)
        }

        barreRecherche.setOnItemClickListener { _, _, position, _ ->
            présentateur.traiter_lancer_recherche(position)
        }


        boutonRecherche.setOnClickListener {
            présentateur.traiter_boutton_recherche()
        }


    }

    fun mettre_a_jour_suggestion(suggestions: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestions)
        barreRecherche.setAdapter(adapter)
        barreRecherche.threshold = 1 // Déclenche les suggestions après 1 caractère
    }

    fun charger_barre_recherche(position: Int): String {
        return barreRecherche.adapter.getItem(position).toString()
    }

    fun charger_radio_id(): Int{
        return radioTitre.id
    }

    fun charger_group_radio_checked_id(): Int{
        return groupeRadio.checkedRadioButtonId
    }

    fun charger_text_barre_recherche(text: String){
        barreRecherche.setText(text)
    }

    fun avoir_text_barre_recherche(): String{
        return barreRecherche.text.toString()
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

