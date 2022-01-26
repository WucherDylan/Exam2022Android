package com.example.exam_android_2022

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.exam_android_2022.Dao.CodeNafDao
import com.example.exam_android_2022.model.CodeNaf

@Database(entities = [CodeNaf::class], version = 1)
abstract class Naf_DataBase:RoomDatabase() {
    abstract fun codeNafDao(): CodeNafDao
    companion object {
        var INSTANCE: Naf_DataBase? = null

        fun getDatabase(context: MainActivity): Naf_DataBase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, Naf_DataBase::class.java, "naf.db")
                    .createFromAsset("activite_naf.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }

}