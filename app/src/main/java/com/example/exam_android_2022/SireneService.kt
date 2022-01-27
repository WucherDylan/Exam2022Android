package com.example.exam_android_2022

import android.util.JsonReader
import android.util.JsonToken
import com.example.exam_android_2022.model.Sirene
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class SireneService() {
    private val apiUrl =
        "https://entreprise.data.gouv.fr/api/sirene/v1/full_text/%s?code_postal=%s&departement=%s&activite_principale=%s&per_page=30"

    fun getSirene(query: String, cp: String = "", dep: String = "",naf:String=""): List<Sirene> {
        val url = URL(String.format(apiUrl, query, cp, dep,naf))
        var conn: HttpsURLConnection? = null
        try {
            conn = url.openConnection() as HttpsURLConnection
            conn.connect()
            if (conn.responseCode != HttpsURLConnection.HTTP_OK) {
                return emptyList()
            }

            val inputStream = conn.inputStream ?: return emptyList()
            val reader = JsonReader(inputStream.bufferedReader())
            val result = mutableListOf<Sirene>()
            reader.beginObject()
            while (reader.hasNext()) {
                if (reader.nextName() == "etablissement") {
                    reader.beginArray()
                    while (reader.hasNext()) {
                        val sirene = Sirene()
                        reader.beginObject()
                        while (reader.hasNext()) {
                            val name = reader.nextName()
                            if (name == "siren" && reader.peek() !== JsonToken.NULL) {
                                sirene.siren = reader.nextString()
                            } else if (name == "siret" && reader.peek() !== JsonToken.NULL) {
                                sirene.siret = reader.nextString()
                            } else if (name == "l1_normalisee" && reader.peek() !== JsonToken.NULL) {
                                sirene.l1_nomarliseeNomSociete = reader.nextString()
                            } else if (name == "l4_normalisee" && reader.peek() !== JsonToken.NULL) {
                                sirene.l4_normaliseeAdresse = reader.nextString()
                            } else if (name == "code_postal" && reader.peek() !== JsonToken.NULL) {
                                sirene.l6_normaliseeCP = reader.nextString()
                            } else if (name == "region" && reader.peek() !== JsonToken.NULL) {
                                sirene.libelle_region = reader.nextString()
                            } else if (name == "departement" && reader.peek() !== JsonToken.NULL) {
                                sirene.libelle_departement = reader.nextString()
                            } else if (name == "libelle_activite_principale_entreprise" && reader.peek() !== JsonToken.NULL) {
                                sirene.libelle_activite_principale_entreprise = reader.nextString()
                            } else if (name == "tranche_effectif_salarie_entreprise" && reader.peek() !== JsonToken.NULL) {
                                sirene.tranche_effectif_salarie_entreprise = reader.nextString()
                            } else if (name == "latitude" && reader.peek() !== JsonToken.NULL) {
                                sirene.latitude = reader.nextString()
                            } else if (name == "longitude" && reader.peek() !== JsonToken.NULL) {
                                sirene.longitutde = reader.nextString()
                            } else {
                                reader.skipValue()
                            }
                        }
                        reader.endObject()
                        result.add(sirene)


                    }
                    reader.endArray()
                } else {
                    reader.skipValue()
                }
            }
            reader.endObject()
            reader.close()
            result.sort()
            return result
        } catch (e: IOException) {
            return emptyList()
        } finally {
            conn?.disconnect()
        }
    }
}