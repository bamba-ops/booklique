package com.crosemont.booklique.sourcededonnées

import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import Livre
import com.google.gson.GsonBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SourceDeDonnéesLivreHTTP {

    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, object : com.google.gson.TypeAdapter<Date>() {
            private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            override fun write(out: com.google.gson.stream.JsonWriter, value: Date?) {
                out.value(value?.let { dateFormat.format(it) })
            }

            override fun read(input: com.google.gson.stream.JsonReader): Date? {
                return try {
                    val dateStr = input.nextString()
                    dateFormat.parse(dateStr)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        })
        .create()

    fun obtenirTousLivres(): List<Livre> {
        val url = "https://6751e069d1983b9597b4ada0.mockapi.io/api/v1/livres"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Erreur lors de la récupération des livres : ${response.code}")

            val body = response.body?.string() ?: throw Exception("Réponse vide")
            val type = object : TypeToken<List<Livre>>() {}.type

            // Désérialisation en utilisant le Gson configuré
            return gson.fromJson(body, type)
        }
    }
}
