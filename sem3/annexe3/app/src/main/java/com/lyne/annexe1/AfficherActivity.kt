package com.kawtar.annexe1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.kawtar.annexe1.R
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

class AfficherActivity : AppCompatActivity() {
    lateinit var liste: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_afficher)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        liste = findViewById(R.id.listmemo)

        liste.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trierMemo())
    }
    fun trierMemo():  ArrayList<String>{
        val triee = ArrayList<String>()
        var listeActuelle : ArrayList<Memo>? = null // le type soit arraylist soit null
        try {
            listeActuelle = SingletonSimple.getListe()
            listeActuelle.sortBy{it.echeance}
            // 1 ere methode
            listeActuelle.forEach {it -> triee.add(it.memoTel)  }

            // 2 eme methode
            for(memo in listeActuelle){
                triee.add ( memo.memoTel)
            }

        }
        catch(f:FileNotFoundException){
            Toast.makeText(this,"pas de fichier", Toast.LENGTH_LONG).show()
            finish()
        }
        return triee
    }
    fun lireMemos(): ArrayList<String>
    {
            val a = ArrayList<String>()
        try {
            val fis = openFileInput("fichier.txt")
            val isr = InputStreamReader(fis)

            val br = BufferedReader(isr)


            br.use{
                br.forEachLine { ligne -> a.add(ligne) }

                //a = br.readLines() as ArrayList<String> //as utilisé pour transtypé
            }

        }
        catch(e: FileNotFoundException){
            val toast = Toast.makeText(this,"il n'ya pas de memo a afficher ", Toast.LENGTH_LONG)
            toast.show()
            finish()
        }
        return a
    }
}
