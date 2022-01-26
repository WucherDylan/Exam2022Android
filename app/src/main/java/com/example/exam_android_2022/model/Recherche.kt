package com.example.exam_android_2022.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*
import java.io.Serializable

@Entity(
    indices = [Index(value = ["id"],unique =true)]
)
data class Recherche (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var nom: String,
    var dep: String?,
    var cp: String?,
    var date_ajout: String,
    var archive: Boolean = false,
    var codeNaf : String?
    ):Serializable
{
    override fun toString(): String {
        return "$nom \n $date_ajout"
    }
}
