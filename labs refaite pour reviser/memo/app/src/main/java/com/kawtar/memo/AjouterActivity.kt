package com.kawtar.memo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class AjouterActivity : AppCompatActivity() {

    lateinit var add : TextView
    lateinit var buttonAdd : Button
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


        buttonAdd.setOnClickListener {
            var text = add.text.toString()
            val fos = openFileOutput("fichier.txt",MODE_APPEND)
            val osw = OutputStreamWriter(fos)
            val bw = BufferedWriter(osw)
            bw.use{
                bw.write(text)
                bw.newLine()
                finish()
            }
        }
    }

}