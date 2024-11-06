package com.crosemont.booklique.Présentation.Réservation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.crosemont.booklique.R

class Vue : Fragment() {

    private lateinit var imageView: ImageView
    private val imageUrl = "https://i.imghippo.com/files/Al2125oNk.png"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.image_livre_reservation)

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }
}
