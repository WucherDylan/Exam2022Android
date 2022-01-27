package com.example.exam_android_2022

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.exam_android_2022.model.Recherche

class ArchiveActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_maint_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuRecherche -> {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menuHistorique -> {
                val intent = Intent(applicationContext, Historique::class.java)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        val db = LaDataBase.getDataBase(context = MainActivity())
        val RechDao = db.rechercheDao()
        val listRecherche = findViewById<ListView>(R.id.listRecherche)
        val result = RechDao.historiqueArchive()
        listRecherche.adapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            result
        )

        listRecherche.setOnItemClickListener { _, _, position, _ ->
            val recherche = listRecherche.adapter.getItem(position) as Recherche
            val intent = Intent(applicationContext, ResultatActivity::class.java)
            intent.putExtra("recherche", recherche)
            startActivity(intent)
        }

    }
}
