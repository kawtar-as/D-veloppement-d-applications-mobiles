package com.kawtar.annexe1b

import android.R.attr.text
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        calculerNbLignes()
        ecrireNom()
    }
    fun calculerNbLignes () : Int{

        val fis = openFileInput("whatever.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)
        var compteur = 0
        var lines = br.readLine()

        br.use {
            bw.write(text)
            bw.newLine()
            finish()
        }
        val b = a.size
        println(b)
        return b
    }
    fun ecrireNom () {
        var text = "kawtar"
        val fos = openFileOutput("whatever.txt", MODE_APPEND)
        val osw = OutputStreamWriter(fos)
        val bw = BufferedWriter(osw)
        bw.use {
            bw.write(text)
            bw.newLine()
            finish()
        }
    }
    fun compteMots(): Int {
        var compteur = 0
        val fis = openFileInput("whatever.txt", MODE_APPEND)
        val scan = Scanner(fis)
        // on utilise le délimiteur par défaut
        scan.use {
        while (scan.hasNext()) {
            scan.next()
            compteur++
        }
        }
        return compteur
    }
}