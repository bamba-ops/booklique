package com.crosemont.booklique

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.crosemont.booklique.Présentation.Accueil.Vue
import com.crosemont.booklique.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_accueil -> replaceFragment(Vue())
                R.id.nav_reservation -> replaceFragment(Reservation())
                //R.id.nav_recherche -> replaceFragment(Recherche())
               // R.id.nav_historique -> replaceFragment(Historique())
                else -> {

                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}