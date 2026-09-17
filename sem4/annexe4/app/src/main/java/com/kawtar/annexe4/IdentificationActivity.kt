package com.kawtar.annexe4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IdentificationActivity : AppCompatActivity() {
    lateinit var nom: EditText;
    lateinit var prenom: EditText;
    lateinit var confirmer: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_identification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom)
        confirmer = findViewById(R.id.confirmer)

        confirmer.setOnClickListener {
            val retour = Intent() // intent de retour
            var u = Utilisateur( prenom.text.toString(),nom.text.toString())
            retour.putExtra("util",u)
            setResult(RESULT_OK,retour)
            finish()
        }
    }
}