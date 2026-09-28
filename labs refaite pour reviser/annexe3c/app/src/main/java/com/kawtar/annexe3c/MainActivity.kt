package com.kawtar.annexe3c

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText

import android.widget.LinearLayout

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream


class MainActivity : AppCompatActivity() {

    lateinit var dent1: LinearLayout
    lateinit var dent2: LinearLayout
    var dent : Dent?= null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dent1 = findViewById(R.id.dent1)
        dent2 = findViewById(R.id.dent2)



        // DÉSÉRIALISATION (Lecture des données sauvegardées)
        try{
            val ofi = openFileInput("fichier.ser")
            var ois = ObjectInputStream(ofi)
            ois.use {
                val objetDent =  ois.readObject() as Dent // transtypage
                (dent1.getChildAt(0) as EditText).setText(objetDent.numero.toString())
                (dent1.getChildAt(1) as CheckBox).isChecked=objetDent.traited
                (dent1.getChildAt(2) as EditText).setText(objetDent.note.toString())
            }
        } catch (e: Exception){

        }


    }

    override fun onStop() {
        super.onStop()

        val num = (dent1.getChildAt(0) as EditText).text.toString().toInt()
        val etat = (dent1.getChildAt(1) as CheckBox).isChecked
        val note = (dent1.getChildAt(2) as EditText).text.toString()
        val ofo = openFileOutput("fichier.ser",MODE_PRIVATE)
        val oos = ObjectOutputStream(ofo)
        oos.use {
            oos.writeObject(Dent(num,etat,note) )
        }

    }
}