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
                    // Vérifiez si vous êtes déjà dans le fragment Home
                    if (navController.currentDestination?.id == R.id.accueil) {
                        // Rechargez le fragment Home
                        navController.popBackStack(R.id.accueil, true)
                        navController.navigate(R.id.accueil)
                    } else {
                        navController.navigate(R.id.accueil)
                    }
                    true
                }
                R.id.reservation -> {
                    navController.navigate(R.id.reservation)
                    true
                }
                R.id.recherche -> {
                    navController.navigate(R.id.recherche)
                    true
                }
                R.id.historique -> {
                    navController.navigate(R.id.historique)
                    true
                }
                else -> false
            }
        }
    }
}