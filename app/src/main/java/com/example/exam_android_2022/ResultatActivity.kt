package com.example.exam_android_2022

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.exam_android_2022.Dao.SireneDao
import com.example.exam_android_2022.model.Recherche
import com.example.exam_android_2022.model.Sirene

class ResultatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultat)
        val recherche = intent?.extras?.get("recherche") as? Recherche ?:return
        val db = LaDataBase.getDataBase(context = MainActivity())
        val sirene = db.sireneDao()
        val result = sirene.rechercheSireneHistorique(recherche.id!!)
        val listResultat =  findViewById<ListView>(R.id.listResultat)
        listResultat.adapter = ArrayAdapter<Sirene>(
            applicationContext,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            result
        )
        listResultat.setOnItemClickListener { _, _, position, _ ->
            val sirene = listResultat.adapter.getItem(position) as Sirene
            val intent = Intent(applicationContext, InfoActivity::class.java)
            intent.putExtra("sirene", sirene)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.noArchive->{
                val intent = Intent(applicationContext, Historique::class.java)
                startActivity(intent)
                true
            }
            R.id.archive->{
                val intent = Intent(applicationContext, ArchiveActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menuRecherche -> {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menuExit -> {
                AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Sortir")
                    .setMessage("Fermer l'application?")
                    .setPositiveButton("Oui", DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }).setNegativeButton("Non", null).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}