package com.example.exam_android_2022

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.Menu
import android.widget.*
import com.example.exam_android_2022.model.Recherche
import com.example.exam_android_2022.model.RechercheSirene
import com.example.exam_android_2022.model.Sirene
import java.text.SimpleDateFormat
import java.util.*

import android.widget.RelativeLayout


class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility", "CutPasteId", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = LaDataBase.getDataBase(this)
        val dbNaf = Naf_DataBase.getDatabase(this)
        val nafDao = dbNaf.codeNafDao()
        val sireneDAO = db.sireneDao()
        val rechercheDAO = db.rechercheDao()
        sireneDAO.archiveSirene()
        rechercheDAO.archiveRecherche()
        sireneDAO.deleteSirene3Mois()
        rechercheDAO.deleteRecherche3Mois()
        val rechercheSireneDAO = db.rechercheSireneDao()
        val svc = SireneService()
        val listSirene = findViewById<ListView>(R.id.listSirene)
        val autoComplet = findViewById<AutoCompleteTextView>(R.id.editQuery)
        val autoCompletCP = findViewById<AutoCompleteTextView>(R.id.editCP)
        val autoCompletDEP = findViewById<AutoCompleteTextView>(R.id.editDep)
        val autoCompletNaf = findViewById<AutoCompleteTextView>(R.id.editNaf)
        val listCodeNaf = nafDao.rechercheCodeNaf()
        val NafAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            listCodeNaf
        )
        autoCompletNaf.setAdapter(NafAdapter)
        val btn = findViewById<Switch>(R.id.switch1)
        val params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )


        btn.setOnClickListener {
            if (btn.isChecked()) {
                autoCompletCP.visibility = View.VISIBLE
                autoCompletDEP.visibility = View.VISIBLE
                autoCompletNaf.visibility = View.VISIBLE
                findViewById<TextView>(R.id.txt1).visibility = View.VISIBLE
                btn.setText("-")
                params.addRule(RelativeLayout.BELOW, R.id.editNaf)
                listSirene.layoutParams = params


            } else {
                autoCompletCP.visibility = View.INVISIBLE
                autoCompletDEP.visibility = View.INVISIBLE
                autoCompletNaf.visibility = View.INVISIBLE
                findViewById<TextView>(R.id.txt1).visibility = View.INVISIBLE
                autoCompletCP.setText("")
                autoCompletDEP.setText("")
                autoCompletNaf.setText("")
                btn.setText("+")
                params.addRule(RelativeLayout.BELOW, R.id.switch1)
                listSirene.layoutParams = params


            }
        }




        autoComplet.setOnTouchListener(
            View.OnTouchListener(
                fun(v: View, event: MotionEvent): Boolean {
                    val suggestion = rechercheDAO.affiche5der().toMutableList()
                    val listSearchHistoryAdapter =
                        ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestion)
                    autoComplet.setAdapter(listSearchHistoryAdapter)
                    autoComplet.showDropDown()
                    return false
                }
            )
        )
        autoCompletCP.setOnTouchListener(
            View.OnTouchListener(
                fun(v: View, event: MotionEvent): Boolean {
                    val suggestionCP = rechercheDAO.affiche5derCP().toMutableList()
                    val listSearchHistoryCP =
                        ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestionCP)
                    autoCompletCP.setAdapter(listSearchHistoryCP)
                    autoCompletCP.showDropDown()
                    return false
                }
            )
        )
        autoCompletDEP.setOnTouchListener(
            View.OnTouchListener(
                fun(v: View, event: MotionEvent): Boolean {
                    val suggestionDep = rechercheDAO.affiche5derDep().toMutableList()
                    val listSearchHistoryDep =
                        ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestionDep)
                    autoCompletDEP.setAdapter(listSearchHistoryDep)
                    autoCompletDEP.showDropDown()
                    return false
                }
            )
        )
        findViewById<ImageButton>(R.id.buttonQuery).setOnClickListener {
            val formatDate = SimpleDateFormat("yyyy-MM-dd")
            val dateDuJour = formatDate.format(Date())
            val query = findViewById<AutoCompleteTextView>(R.id.editQuery).text.toString()
            val cp = findViewById<EditText>(R.id.editCP).text.toString()
            val dep = findViewById<EditText>(R.id.editDep).text.toString()
            var naf = findViewById<EditText>(R.id.editNaf).text.toString()
            val progressBar = findViewById<ProgressBar>(R.id.queryProgressBar)
            val btn = findViewById<ImageButton>(R.id.buttonQuery)
            if (query.isNotBlank() && query.isNotEmpty()) {
                if ((cp.isEmpty() && dep.isEmpty()) || (cp.isNotEmpty() && dep.isEmpty()) ||
                    cp.isEmpty() && dep.isNotEmpty()
                ) {
                    Thread(Runnable {
                        runOnUiThread {
                            progressBar.visibility = View.VISIBLE
                            listSirene.visibility = View.INVISIBLE
                            btn.visibility = View.INVISIBLE
                        }
                        if (naf.isNotBlank() && naf.isNotEmpty()) {
                            naf = nafDao.convertRecherche(naf).toString()
                        }
                        val result: List<Sirene>
                        if (rechercheDAO.existe(
                                nom = query,
                                cp = cp,
                                dep = dep,
                                codeNaf = naf
                            ) == 1
                        ) {
                            val recherche =
                                rechercheDAO.getRecherche(
                                    nom = query,
                                    cp = cp,
                                    dep = dep,
                                    codeNaf = naf
                                )
                            result = sireneDAO.rechercheSirene(recherche!!.id!!)

                        } else {
                            //ajout de la recherche dans la BDD
                            rechercheDAO.insert(
                                Recherche(
                                    nom = query,
                                    cp = cp,
                                    dep = dep,
                                    date_ajout = dateDuJour,
                                    codeNaf = naf
                                )
                            )
                            result = svc.getSirene(query, cp, dep, naf)
                            result.forEach() {
                                var id: Long
                                if (sireneDAO.sirenExiste(siren = it.siren) == 0) {
                                    it.date_ajout = dateDuJour
                                    sireneDAO.insert(it)
                                    id = sireneDAO.dernierEnregistrement()
                                } else {
                                    val siren = sireneDAO.getSirene(it.siren)
                                    id = siren!!.id!!
                                }
                                rechercheSireneDAO.insert(
                                    RechercheSirene
                                        (
                                        sirene_id = id,
                                        recherche_id = rechercheDAO.dernierEnregistrement()
                                    )
                                )
                            }
                        }
                        runOnUiThread {
                            listSirene.adapter = ArrayAdapter<Sirene>(
                                applicationContext,
                                android.R.layout.simple_list_item_1,
                                android.R.id.text1,
                                result
                            )
                            progressBar.visibility = View.INVISIBLE
                            listSirene.visibility = View.VISIBLE
                            btn.visibility = View.VISIBLE
                        }
                    }).start()
                } else {
                    listSirene.visibility = View.INVISIBLE
                    Toast.makeText(
                        this, "Vous ne pouvez pas remplir le champ département " +
                                "et code postal en même temps", Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Veuillez entrer une valeur", Toast.LENGTH_SHORT).show()
            }
        }
        listSirene.setOnItemClickListener { _, _, position, _ ->
            val sirene = listSirene.adapter.getItem(position) as Sirene
            val intent = Intent(applicationContext, InfoActivity::class.java)
            intent.putExtra("sirene", sirene)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_maint_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.noArchive -> {
                val intent = Intent(applicationContext, Historique::class.java)
                startActivity(intent)
                true
            }
            R.id.archive -> {
                val intent = Intent(applicationContext, ArchiveActivity::class.java)
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


