package com.kawtar.annexe1b_rev

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner

class MainActivity : AppCompatActivity() {

    lateinit var a : TextView
    lateinit var b : TextView
    lateinit var c : TextView
    lateinit var d : TextView
    lateinit var e : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        a = findViewById(R.id.A)
        b = findViewById(R.id.B)
        c = findViewById(R.id.C)
        d = findViewById(R.id.D)
        e = findViewById(R.id.E)

        a.text = " nombre de lignes " +calculerNbreLigne().toString();
        b.text = " nombre de carac " +calculerNbreCarac().toString();
        c.text = " nombre de c " + calculerNbreC().toString();
        d.text = "nombre de mots dans txt" + calculerNbreMots().toString()
        ecrireNom(e.text.toString()) // va dans le champ ecrit le nom que on a entrer
    }
    //Une fonction retournant le nombre de lignes que compte votre fichier texte
    fun calculerNbreLigne(): Int{
        var liste = ArrayList<String>()
        try {
            val fis = openFileInput("file.txt")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            br.forEachLine { ligne -> liste.add(ligne) }

        }
        catch(e: FileNotFoundException){
            val toast = Toast.makeText(this,"fichier introuvable",LENGTH_LONG).show()
            finish()
        }
        return liste.size
    }

    //Une fonction retournant le nombre de caractères que compte votre fichier texte

    fun calculerNbreCarac(): Int{
        var compteur = 0
        try {
            val fis = openFileInput("file.txt")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            br.forEachLine { compteur += it.length}

        }
        catch(e: FileNotFoundException){
            val toast = Toast.makeText(this,"fichier introuvable",LENGTH_LONG).show()
            finish()
        }
        return compteur
    }

    //Une fonction retournant le nombre de « c » que comprend votre fichier texte
    fun calculerNbreC(): Int{
        var compteur = 0

        val fis = openFileInput("file.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)
        br.forEachLine {
          line -> for (i in line){
              if(i == 'c' || i == 'C'){
                  compteur ++
              }
          }
        }

        return compteur
    }

    //Une fonction permettant d’écrire votre nom sur une ligne située à la fin du fichier texte
    fun ecrireNom(nom : String): Unit{
        val fos = openFileOutput("file.txt",MODE_APPEND)
        val osw = OutputStreamWriter(fos)
        val br = BufferedWriter(osw)
        br.use{
            br.write(nom)
            br.newLine()
            val toast = Toast.makeText(this,"Mot ajouté", Toast.LENGTH_LONG)
            toast.show()
        }

    }
    // une fonction pour calculer combien de mot dans le fichier
    fun calculerNbreMots() : Int{
        var compteur = 0

        val fis = openFileInput("file.txt")
        val scan = Scanner(fis)
        while (scan.hasNext()){
            scan.next()
            compteur++
        }


        return compteur
    }
}