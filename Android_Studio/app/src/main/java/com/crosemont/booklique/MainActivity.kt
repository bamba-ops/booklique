package com.crosemont.booklique

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtenir le NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // Configurer le BottomNavigationView avec le NavController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_menu)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        // Ajoutez un écouteur pour gérer le clic sur l'icône "Accueil" lorsque vous y êtes déjà
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.accueil -> {
                    // Vérifiez si vous êtes déjà dans le fragment "Accueil"
                    if (navController.currentDestination?.id == R.id.accueil) {
                        // Rechargez le fragment Accueil
                        navController.popBackStack(R.id.accueil, true)
                        navController.navigate(R.id.accueil)
                    } else {
                        navController.navigate(R.id.accueil)
                    }
                    true
                }
                R.id.reservation -> {
                    // Vérifiez si vous êtes déjà dans le fragment "Réservation"
                    if (navController.currentDestination?.id == R.id.reservation) {
                        // Rechargez le fragment Réservation
                        navController.popBackStack(R.id.reservation, true)
                        navController.navigate(R.id.reservation)
                    } else {
                        navController.navigate(R.id.action_accueil_to_reservation)
                    }
                    true
                }
                R.id.recherche -> {
                    // Vérifiez si vous êtes déjà dans le fragment "Recherche"
                    if (navController.currentDestination?.id == R.id.recherche) {
                        // Rechargez le fragment Recherche
                        navController.popBackStack(R.id.recherche, true)
                        navController.navigate(R.id.recherche)
                    } else {
                        navController.navigate(R.id.action_accueil_to_recherche)
                    }
                    true
                }
                R.id.historique -> {
                    // Vérifiez si vous êtes déjà dans le fragment "Historique"
                    if (navController.currentDestination?.id == R.id.historique) {
                        // Rechargez le fragment Historique
                        navController.popBackStack(R.id.historique, true)
                        navController.navigate(R.id.historique)
                    } else {
                        navController.navigate(R.id.historique)
                    }
                    true
                }
                else -> false
            }
        }
    }
}