package com.example.exam_android_2022.Dao

import androidx.room.*
import com.example.exam_android_2022.model.Recherche
import com.example.exam_android_2022.model.Sirene

@Dao
interface RechercheDao {

    @Query("Select * from Recherche where nom=:nom and cp=:cp and dep=:dep and codeNaf=:codeNaf and archive=0")
    fun getRecherche(nom: String, cp: String, dep: String, codeNaf: String): Recherche

    @Query("delete from recherche where julianday('now') - julianday(date_ajout) > 90")
    fun deleteRecherche3Mois()

    @Query("Select * from recherche where archive =0")
    fun historique(): List<Recherche>

    @Query("Select * from recherche where archive =1")
    fun historiqueArchive(): List<Recherche>

    @Query("Select nom from Recherche where  archive=0 Order by id Desc limit 5")
    fun affiche5der(): Array<String>

    @Query("Select cp FROM Recherche  where cp != '' and archive=0 Order by id DESC limit 5")
    fun affiche5derCP(): Array<String>

    @Query("Select dep from Recherche where dep !='' and archive=0 Order by id DESC limit 5")
    fun affiche5derDep(): Array<String>


    @Query("Select max(id) From Recherche ")
    fun dernierEnregistrement(): Long

    @Query("UPDATE Recherche set archive = 1 where julianday('now') - julianday(date_ajout) > 1")
    fun archiveRecherche()

    @Query("Select count (*) From Recherche where nom=:nom and cp=:cp and dep=:dep and codeNaf=:codeNaf and archive=0")
    fun existe(nom: String, cp: String, dep: String, codeNaf: String): Int

    @Insert
    fun insert(recherche: Recherche): Long

    @Update
    fun update(recherche: Recherche)

    @Delete
    fun delete(recherche: Recherche)
}