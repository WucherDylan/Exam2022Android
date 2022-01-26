package com.example.exam_android_2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.exam_android_2022.model.Recherche

class ArchiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)

        val db = LaDataBase.getDataBase(context = MainActivity())
        val RechDao = db.rechercheDao()
        val listRecherche = findViewById<ListView>(R.id.listRecherche)
        val result = RechDao.HistoriqueArchive()
        listRecherche.adapter= ArrayAdapter(applicationContext,
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
