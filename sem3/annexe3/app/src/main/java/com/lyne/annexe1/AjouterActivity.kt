package com.kawtar.annexe1

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AjouterActivity : AppCompatActivity() {
    lateinit var champMemo: EditText
    lateinit var boutonAjouterMemo : Button
    lateinit var boutonAjouterDate : Button
    lateinit var champDate : EditText
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
        boutonAjouterMemo = findViewById(R.id.boutonAjouterDate)
        boutonAjouterDate = findViewById(R.id.boutonAjouterMemo)
        champMemo = findViewById(R.id.editTextText)
        champDate =  findViewById(R.id.textView)

        boutonAjouterMemo.setOnClickListener{
            //lorsqu'on clique sur le bouton
            var text = champMemo.text.toString()
            SingletonSimple.ajouterMemo(Memo(text,dateChoisie))
            finish()


        }
        boutonAjouterDate.setOnClickListener{
            // faire afficher un date pickerdialog
            val d = DatePickerDialog(this)
            // placer un ecouteur sur le datePickerDialog
            d.setOnDateSetListener { d, annee, mois, jour -> dateChoisie = LocalDate.of(annee,mois+1,jour)
                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                val text: String? = dateChoisie.format(formatter)
                champDate.setText(text)

            }
            d.show()
        }
    }
    override fun onStop(){
        super.onStop()
         try{
             SingletonSimple.serialiserListe(applicationContext)// plutot que this pcqnotre singleton est visible dans toutel'application

         }
         catch(e: Exception){}
    }
}