package com.crosemont.booklique

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SflashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sflash_screen)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this@SflashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
