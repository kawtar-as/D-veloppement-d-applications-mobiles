package com.kawtar.annexe4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IdentificationActivity : AppCompatActivity() {
    lateinit var nom : TextView
    lateinit var prenom : TextView
    lateinit var confirmer : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_identification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nom = findViewById(R.id.nom)
        prenom = findViewById(R.id.prenom)
        confirmer = findViewById(R.id.Confirmer)

        confirmer.setOnClickListener {
            val i = Intent()
            var u = Utilisateur(nom.text.toString(),prenom.text.toString())
            i.putExtra("util",u)
            setResult(RESULT_OK,i)
            finish()

        }

    }

}