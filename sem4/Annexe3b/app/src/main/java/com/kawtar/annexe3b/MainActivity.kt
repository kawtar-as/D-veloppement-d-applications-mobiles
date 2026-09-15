package com.kawtar.annexe3b

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Objects

class MainActivity : AppCompatActivity() {
    lateinit var sonnerie: SeekBar;
    lateinit var media: SeekBar;
    lateinit var notifs: SeekBar;
    var volume : Volume? = null // typ volume ou null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sonnerie = findViewById(R.id.son)
        media = findViewById(R.id.med)
        notifs = findViewById(R.id.nott)

        // deserialisation
        recupererVolumes()
    }
        fun recupererVolumes(){
            val fis: FileInputStream = openFileInput("seialisation.ser")
            val oos = ObjectInputStream(fis) // tampon
            oos.use {
                oos.readObject() as Volume
                try {
                    sonnerie.progress = volume!!.son
                    media.progress = volume!!.med
                    notifs.setProgress( volume!!.noti )// c'est pas null
                } catch (e: Exception) {
                    Toast.makeText(this," 1 EREE UTILISATION DONC PAS  de fichier de sérialisatiion ",LENGTH_LONG).show()

                }
            }
        }
    override fun onStop(){
        super.onStop()
        try{
            val fos: FileOutputStream = openFileOutput("seialisation.ser",MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.use {
                oos.writeObject(Volume(sonnerie.progress,media.progress,notifs.progress))
            }

        }
        catch(e: IOException){
            e.printStackTrace()
        }
    }
}