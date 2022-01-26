package com.example.exam_android_2022.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(
    indices = [Index(value = ["id"], unique = true)]
)
data class Sirene(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var siren: String = "",
    var siret: String = "",
    var l1_nomarliseeNomSociete: String = "", var l4_normaliseeAdresse: String = "",
    var l6_normaliseeCP: String = "",
    var libelle_region: String = "",
    var libelle_departement: String = "",
    var libelle_activite_principale_entreprise: String = "",
    var tranche_effectif_salarie_entreprise: String = "",
    var archive: Boolean = false,
    var date_ajout: String="",
    var latitude:String="",
    var longitutde:String = ""

):Comparable<Sirene>,Serializable
{
    override fun compareTo(other: Sirene):Int {
        return l1_nomarliseeNomSociete.compareTo(other.l1_nomarliseeNomSociete)
    }

    override fun toString(): String {
        return "$l1_nomarliseeNomSociete,$libelle_departement"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sirene
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
