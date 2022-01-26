package com.example.exam_android_2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.exam_android_2022.model.Recherche


class Historique : AppCompatActivity() {
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
            R.id.Archive -> {
                val intent = Intent(applicationContext, ArchiveActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menuExit -> {
                this.finish()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique2)
        val db = LaDataBase.getDataBase(context = MainActivity())
        val RechDao = db.rechercheDao()
        val listRecherche = findViewById<ListView>(R.id.listRecherche)
        val result = RechDao.Historique()
        listRecherche.adapter=ArrayAdapter(applicationContext,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            result)

        listRecherche.setOnItemClickListener { _, _, position, _ ->
            val recherche = listRecherche.adapter.getItem(position) as Recherche
            val intent = Intent(applicationContext, ResultatActivity::class.java)
            intent.putExtra("recherche", recherche)
            startActivity(intent)
        }

            }
}

