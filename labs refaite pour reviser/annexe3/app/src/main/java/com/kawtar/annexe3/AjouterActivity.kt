package com.kawtar.annexe3

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AjouterActivity : AppCompatActivity() {

    lateinit var add : TextView
    lateinit var buttonAdd : Button
    lateinit var dateecheance : Button
    lateinit var  champDate : TextView
    var dateChoisie = LocalDate.now().plusDays(1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        add = findViewById(R.id.addText)
        buttonAdd = findViewById(R.id.onAdd)
        dateecheance = findViewById(R.id.datepicker)
        champDate = findViewById(R.id.champDate)


        buttonAdd.setOnClickListener {
            var text = add.text.toString()
            SingletonSimple.ajouterMemo(Memo(text,dateChoisie))
            finish()
        }

        // pour la date la
        dateecheance.setOnClickListener {
            // afficher un date pickerdialog
            val d = DatePickerDialog(this)
            // on place un ecouteur sur le datep
            //enregistre la date sélectionnée dans votre variable dateChoisie sous la forme d'un objet Java/Kotlin LocalDate.
            d.setOnDateSetListener { d, jour, mois, annee ->  dateChoisie = LocalDate.of(annee,mois+1,jour)
            val formatter = DateTimeFormatter.ofPattern("dd MMMM YYYY")
                val text: String? = dateChoisie.format(formatter)
                champDate.setText(text)
            }
            d.show()
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            SingletonSimple.serialiserListe(applicationContext) //Lié à l'Application (longue)
        }
        catch(e: Exception){}
    }
}