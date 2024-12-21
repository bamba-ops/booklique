package com.crosemont.booklique.sourcededonnées

import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import Livre
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
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

            return gson.fromJson(body, type)
        }
    }

    fun obtenirLivreParIsbn(isbn: String): Livre? {
        val url = "https://6751e069d1983b9597b4ada0.mockapi.io/api/v1/livres/$isbn"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Erreur lors de la récupération du livre : ${response.code}")

            val body = response.body?.string() ?: throw Exception("Réponse vide")
            return gson.fromJson(body, Livre::class.java)
        }
    }

    fun modifierLivreParIsbn(isbn: String, livre: Livre): Livre?{
        val url = "https://6751e069d1983b9597b4ada0.mockapi.io/api/v1/livres/$isbn"

        // Convertir l'objet `Livre` en JSON
        val json = gson.toJson(livre)

        // Créer le corps de la requête
        val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())

        // Construire la requête PUT
        val request = Request.Builder()
            .url(url)
            .put(body)
            .build()

        // Exécuter la requête et traiter la réponse
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Erreur lors de la modification du livre : ${response.code}")
            }

            val responseBody = response.body?.string() ?: throw Exception("Réponse vide")
            return gson.fromJson(responseBody, Livre::class.java)
        }
    }
}
