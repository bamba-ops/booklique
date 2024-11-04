package com.crosemont.booklique

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class Reservation : Fragment() {

    private lateinit var imageView: ImageView
    private val imageUrl = "https://i.imghippo.com/files/Al2125oNk.png"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialisez imageView après avoir gonflé la vue
        imageView = view.findViewById(R.id.image_livre_reservation)

        // Chargez l'image avec Glide
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }
}
