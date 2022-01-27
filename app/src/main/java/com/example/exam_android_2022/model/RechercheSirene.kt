package com.example.exam_android_2022.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Recherche::class,
            parentColumns = ["id"],
            childColumns = ["recherche_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Sirene::class,
            parentColumns = ["id"],
            childColumns = ["sirene_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RechercheSirene(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val sirene_id: Long,
    val recherche_id: Long
)

