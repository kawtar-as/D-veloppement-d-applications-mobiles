package com.kawtar.annexe3b

import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.ObjectInputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var notification : SeekBar
    lateinit var sonnerie : SeekBar
    lateinit var media : SeekBar
    lateinit var volume: Volume
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        notification = findViewById(R.id.not)
        sonnerie = findViewById(R.id.sonneri)
        media=findViewById(R.id.med)
        recupererVolume()

    }
    fun recupererVolume() :Unit{
        var fis = openFileInput("fichier.ser")
        var ois = ObjectInputStream(fis)
       ois.use {
           try {
               volume = ois.readObject() as Volume
               notification.progress = volume!!.notification
               sonnerie.progress = volume!!.sonnerie
               media.progress = volume!!.media
           }
           catch (e: FileNotFoundException){
               Toast.makeText(this, "fichier pas trouvé", Toast.LENGTH_LONG).show()
           }
       }
    }

    override fun onStop() {
        super.onStop()
        //ICI ON SERIALISE
        try {
            var fos = openFileOutput("fichier.ser",MODE_PRIVATE)
            var oos = ObjectOutputStream(fos)
            oos.use {
                //writeObject peut contenir plusiers objets/données
                oos.writeObject(Volume(sonnerie.progress,media.progress,notification.progress))
            }
        }
        catch (e: FileNotFoundException){
            Toast.makeText(this, "fichier pas trouvé", Toast.LENGTH_LONG).show()
        }

    }
}