package com.example.exam_android_2022

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import com.example.exam_android_2022.Dao.SireneDao
import com.example.exam_android_2022.model.Sirene
import com.google.android.gms.maps.CameraUpdateFactory
import org.w3c.dom.Text
import android.content.DialogInterface




class InfoActivity : AppCompatActivity(),OnMapReadyCallback {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        // Affichage des data
        val sirene = intent?.extras?.get("sirene") as? Sirene ?: return
        // val infoProgressBar = findViewById<ProgressBar>(R.id.infoProgressBar
        findViewById<TextView>(R.id.siren).setText(
            String.format(
                sirene.siren
            )
        )
        findViewById<TextView>(R.id.siret).setText(
            String.format(
                sirene.siret
            )
        )
        findViewById<TextView>(R.id.nomSociete).setText(
            String.format(
                sirene.l1_nomarliseeNomSociete
            )
        )
        findViewById<TextView>(R.id.dep).setText(
            String.format(
                sirene.libelle_departement
            )
        )
        findViewById<TextView>(R.id.CodePostal).setText(
            String.format(
                sirene.l6_normaliseeCP
            )
        )
        findViewById<TextView>(R.id.Region).setText(
            String.format(
                sirene.libelle_region
            )
        )
        findViewById<TextView>(R.id.ActivitePrincipal).setText(
            String.format(
                sirene.libelle_activite_principale_entreprise
            )
        )
        findViewById<TextView>(R.id.Effectif).setText(
            String.format(
                sirene.tranche_effectif_salarie_entreprise
            )
        )
        // Affichage de la map

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(gMap: GoogleMap) {
        val sirene = intent?.extras?.get("sirene") as? Sirene ?: return

        if (sirene.longitutde=="" && sirene.latitude==""){
            val ville = LatLng(0.00, 0.00)
            gMap.moveCamera(CameraUpdateFactory.newLatLng(ville))
        }
        else{
            val ville = LatLng(sirene.latitude.toDouble(), sirene.longitutde.toDouble())
            gMap.addMarker(
                MarkerOptions()
                    .position(ville)
                    .title("Entreprise")
            )
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ville, 12F))
        }
    }
}



