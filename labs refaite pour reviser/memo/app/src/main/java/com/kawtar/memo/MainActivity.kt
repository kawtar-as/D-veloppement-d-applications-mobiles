package com.kawtar.memo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var ajouter : Button
    lateinit var afficher : Button
    lateinit var quitter : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ajouter = findViewById(R.id.bouton_ajouter)
        afficher = findViewById(R.id.bouton_afficher)
        quitter = findViewById(R.id.bouton_quitter)


        val ec= Ecouteur()

        ajouter.setOnClickListener(ec)
        afficher.setOnClickListener(ec)
        quitter.setOnClickListener(ec)

    }
    inner class Ecouteur : View.OnClickListener {
        override fun onClick(v: View?) {
            if(v == ajouter ){
                val i = Intent(this@MainActivity, AjouterActivity::class.java)
                startActivity(i)
            }
            else if(v == afficher) {
                val i = Intent(this@MainActivity, AfficherActivity::class.java)
                startActivity(i)
            }
            else{
                finish()
            }
        }
    }


}