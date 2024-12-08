package com.crosemont.booklique.sourcededonnées

import com.crosemont.booklique.domaine.entité.Reservation
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SourceDeDonnéesReservationHTTP {

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

    fun obtenirToutesReservations(): List<Reservation> {
        val url = "https://6751e069d1983b9597b4ada0.mockapi.io/api/v1/reservations"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Erreur lors de la récupération des réservations : ${response.code}")

            val body = response.body?.string() ?: throw Exception("Réponse vide")
            val type = object : TypeToken<List<Reservation>>() {}.type

            return gson.fromJson(body, type)
        }
    }

    fun obtenirReservationParId(id: Int): Reservation? {
        val url = "https://6751e069d1983b9597b4ada0.mockapi.io/api/v1/reservations/$id"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Erreur lors de la récupération de la réservation : ${response.code}")

            val body = response.body?.string() ?: throw Exception("Réponse vide")
            return gson.fromJson(body, Reservation::class.java)
        }
    }

    fun ajouterReservation(reservation: Reservation): Reservation? {
        val url = "https://6751e069d1983b9597b4ada0.mockapi.io/api/v1/reservations"

        // Convertir en JSON
        val json = gson.toJson(reservation)
        val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())

        // Construire la requête
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        // Envoyer la requête
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Erreur lors de l'ajout de la réservation : ${response.code}")
            }

            val responseBody = response.body?.string() ?: throw Exception("Réponse vide")
            return gson.fromJson(responseBody, Reservation::class.java)
        }
    }
}