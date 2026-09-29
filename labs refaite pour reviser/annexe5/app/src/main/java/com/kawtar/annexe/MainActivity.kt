package com.kawtar.annexe

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
    lateinit var liste : ListView
    val v = ArrayList<HashMap<String, Any>>()
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.list)
        val adapter = SimpleAdapter(this,ajourDansListe(),R.layout.item,
            arrayOf("nom","date","nombre","image"),intArrayOf(R.id.nom,R.id.date,R.id.nombre,R.id.cover))
        liste.adapter = adapter
        //on remplace les paramètres qui ne sont pas nécessaires par des underscore _
        //affiche un (un Toast) contenant la valeur "nom" de l'élément sur lequel l'utilisateur a cliqué
        liste.setOnItemClickListener{ _, _, position, _ ->
            Toast.makeText(this, v.get(position).get("nom").toString(), LENGTH_LONG).show() }
    }
    fun ajourDansListe(): ArrayList<HashMap<String, Any>>{
        var h = HashMap<String, Any>()
        h.put("nom","Touch me")
        h.put("date",2026)
        h.put("image",R.drawable.touchme)
        h.put("nombre",3)
        v.add(h)
        h = HashMap()
        h.put("nom","CACA")
        h.put("date",2024)
        h.put("image",R.drawable.hotboy)
        h.put("nombre",55)
        v.add(h)
        return v
    }
}