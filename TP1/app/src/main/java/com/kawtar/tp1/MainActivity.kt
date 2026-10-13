package com.kawtar.tp1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var metallica : Button
    lateinit var korn : Button
    lateinit var nirvana : Button

    lateinit var ec: Ecouteur
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nirvana = findViewById(R.id.artist1)
        metallica = findViewById(R.id.artist2)
        korn = findViewById(R.id.artist3)

        ec = Ecouteur()
        nirvana.setOnClickListener(ec)
        metallica.setOnClickListener(ec)
        korn.setOnClickListener(ec)


    }
    inner class Ecouteur : View.OnClickListener {
        override fun onClick(v: View?) {
            var artist = ""
            if(v == nirvana ){
                artist = "nirvana"
            }else if (v == metallica){
                artist = "metallica"
            }else{
                artist = "korn"
            }
            val i = Intent(this@MainActivity, AfficherActiviy::class.java)
            i.putExtra("artist",artist)
            startActivity(i)
        }

    }

}