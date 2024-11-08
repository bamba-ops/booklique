package com.crosemont.booklique

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.accueil -> {
                    navController.popBackStack(R.id.accueil, false)
                    navController.navigate(R.id.accueil)
                    true
                }
                R.id.reservation -> {
                    navController.popBackStack(R.id.reservation, false)
                    navController.navigate(R.id.reservation)
                    true
                }
                R.id.recherche -> {
                    navController.popBackStack(R.id.recherche, false)
                    navController.navigate(R.id.recherche)
                    true
                }

                R.id.historique -> {
                    navController.popBackStack(R.id.historique, false)
                    navController.navigate(R.id.historique)
                    true
                }
                else -> false
            }
        }

    }
}