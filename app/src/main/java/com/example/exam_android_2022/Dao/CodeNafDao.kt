package com.example.exam_android_2022.Dao

import androidx.room.Dao
import androidx.room.Query


@Dao
interface CodeNafDao {

//    @Query("Select * from  where CodeNAFAPE LIKE '%':monNaf'%' OR Description LIke '%':monActivite'%'")
//    fun chercheNaf(monNaf:String,monActivite:String):List<String>

    @Query("SELECT CodeNAFAPE  FROM CODE_NAF_APE UNION SELECT Description FROM CODE_NAF_APE")
    fun RechercheCodeNaf():Array<String>

    @Query("SELECT CodeNAFAPE FROM CODE_NAF_APE where Description=:descr OR CodeNAFAPE=:descr")
    fun ConvertRecherche(descr:String):String?
}