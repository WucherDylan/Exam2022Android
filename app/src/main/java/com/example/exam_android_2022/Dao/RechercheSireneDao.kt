package com.example.exam_android_2022.Dao

import androidx.room.*
import com.example.exam_android_2022.model.Recherche
import com.example.exam_android_2022.model.RechercheSirene
import com.example.exam_android_2022.model.Sirene

@Dao
interface RechercheSireneDao {
   @Insert
   fun insert(rechercheSirene : RechercheSirene):Long

   @Update
   fun update(rechercheSirene : RechercheSirene)

   @Delete()
   fun delete(rechercheSirene : RechercheSirene)

}