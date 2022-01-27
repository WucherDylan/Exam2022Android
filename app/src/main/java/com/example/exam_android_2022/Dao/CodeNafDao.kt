package com.example.exam_android_2022.Dao

import androidx.room.Dao
import androidx.room.Query


@Dao
interface CodeNafDao {


    @Query("SELECT CodeNAFAPE  FROM CODE_NAF_APE UNION SELECT Description FROM CODE_NAF_APE")
    fun rechercheCodeNaf(): Array<String>

    @Query("SELECT CodeNAFAPE FROM CODE_NAF_APE WHERE Description=:descr OR CodeNAFAPE=:descr")
    fun convertRecherche(descr: String): String?
}