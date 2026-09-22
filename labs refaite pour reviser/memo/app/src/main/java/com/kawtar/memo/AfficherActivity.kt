package com.kawtar.memo

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

class AfficherActivity : AppCompatActivity() {
    lateinit var liste : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_afficher)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.listeMemo)

        liste.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,lireMemos())
    }
    fun lireMemos() : ArrayList<String>{
        val a = ArrayList<String>()
        try{
            val fis = openFileInput("fichier.txt")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            br.forEachLine {  ligne -> a.add(ligne)}
        }
        catch(e: FileNotFoundException){
            val toast = Toast.makeText(this,"il n y apas de mémo",Toast.LENGTH_LONG)
            toast.show()
            finish()
        }
        return a
    }
}