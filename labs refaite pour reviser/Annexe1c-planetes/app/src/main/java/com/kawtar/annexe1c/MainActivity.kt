package com.kawtar.annexe1c

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.Scanner

class MainActivity : AppCompatActivity() {

    lateinit var liste : ListView
    lateinit var nombresline : TextView
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
        nombresline = findViewById(R.id.nbreLignes)

        val listep = lirePlanete() // mettre la liste des planetes dans une variable
        nombresline.text = "mon fichier a : ${listep.size} lignes"// nombre de ligne dans le fichier

        liste.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listep)
    }
    fun lirePlanete(): ArrayList<Planete>{
        var v = ArrayList<Planete>() // assigner
        try {
            val fis = openFileInput("planetes.txt")
            val scan = Scanner(fis)
           while (scan.hasNext()){
              val nom = scan.next()
               if(scan.hasNextInt()){
                   val number = scan.nextInt()
                    v.add(Planete(nom,number))
               }



        }
        }
        catch(e: FileNotFoundException){
            Toast.makeText(this,"file not found",LENGTH_LONG).show()
        }
        return v
    }
    // 2 eme methode
//    fun afficherListe(): ArrayList<Planete>{
//        val a = ArrayList<Planete>()
//
//        try {
//            val fis = getResources().openRawResource(R.raw.fichier)
//            val s = Scanner(fis)
//            while (s.hasNext()){
//                var temp = Planete(s.next(), s.nextInt())
//                a.add(temp)
//            }
//        } catch (e: FileNotFoundException) {
//            Toast.makeText(this, "il n'y a pas de fichier raw", Toast.LENGTH_LONG).show()
//        }
//        return a
//    }
}