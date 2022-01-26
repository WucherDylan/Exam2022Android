package com.example.exam_android_2022

import android.content.ContentValues
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.exam_android_2022.Dao.RechercheDao
import com.example.exam_android_2022.Dao.RechercheSireneDao
import com.example.exam_android_2022.Dao.SireneDao
import com.example.exam_android_2022.model.Recherche
import com.example.exam_android_2022.model.RechercheSirene
import com.example.exam_android_2022.model.Sirene
import java.io.BufferedReader

@Database (version = 1, entities = [Sirene::class, Recherche::class,RechercheSirene::class])
abstract class LaDataBase: RoomDatabase(){
    abstract fun sireneDao(): SireneDao
    abstract fun rechercheDao(): RechercheDao
    abstract fun rechercheSireneDao() : RechercheSireneDao
    companion object{
        var INSTANCE : LaDataBase? = null

        fun getDataBase(context: MainActivity):LaDataBase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,LaDataBase::class.java,"Examen2022.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}