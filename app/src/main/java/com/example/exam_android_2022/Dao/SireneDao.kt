package com.example.exam_android_2022.Dao

import androidx.room.*
import com.example.exam_android_2022.model.Recherche
import com.example.exam_android_2022.model.Sirene

@Dao
interface SireneDao {
//  @Query("Select * From Sirene Order by l1_nomarliseeNomSociete")
//  fun getAll():List<Sirene>

//  @Query("Select * From sirene where id=:id")
//  fun getById(id : Long):Sirene?

  @Query("Select Sirene.* from Sirene join RechercheSirene ON (Sirene.id = RechercheSirene.sirene_id)  where RechercheSirene.recherche_id =:id  and archive=0")
  fun RechercheSirene (id:Long): List<Sirene>

  @Query("Select * from Sirene where siren=:siren and archive=0")
  fun getSirene (siren:String):Sirene

  @Query("UPDATE Sirene set archive = 1 where julianday('now') - julianday(date_ajout) > 1")
  fun archiveSirene()

  @Query("delete from sirene where julianday('now') - julianday(date_ajout) > 90")
  fun deleteSirene3Mois()

  @Query("Select max(id) From Sirene  ")
  fun dernierEnregistrement():Long

  @Query("Select count(*) from Sirene where siren =:siren and archive = 0")
  fun sirenExiste(siren:String):Int

  @Insert
  fun insert(sirene : Sirene):Long

  @Update
  fun update(sirene : Sirene)

  @Delete()
  fun delete(sirene : Sirene)
}
