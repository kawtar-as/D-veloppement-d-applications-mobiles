package com.kawtar.tp1

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray

class AfficherActiviy : AppCompatActivity() ,Observateur {
    lateinit var liste : ListView
    lateinit var artist :String
    lateinit var k :KlaxonModele
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_afficher_chansons)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.list)
        artist = intent.getStringExtra("artist").toString()
        Toast.makeText(this,"artiste choisi : " + artist.toString(),LENGTH_LONG).show()


        var url = ""
        if(artist == "nirvana"){
            url ="https://itunes.apple.com/search?term=nirvana&media=music&limit=7"
        }else if (artist == "metallica"){
            url ="https://itunes.apple.com/search?term=metallica&media=music&limit=7"
        }else if (artist == "korn"){
            url ="https://itunes.apple.com/search?term=korn&media=music&limit=7"
        }

        k.chercherChanson(this,artist)


    }
    fun decomposerReponse(){
        val remplir = ArrayList<HashMap<String, Any>>()

//        //je fais le tour des éléments du JSONArray pour créer et remplir une Hasmap à la fois
//        for( i in 0..k.{
//            val temp = HashMap<String, Any>()
//            val  chanson = remplir.[i]
//            temp.put("titre",)
//            temp.put("artist", tab.getJSONObject(i).getDouble("artistName"))
//
//            remplir.add(temp)
//
//        }
        val adapt = SimpleAdapter(this,
            remplir,
            R.layout.item,
            arrayOf("titre", "artist"),
            intArrayOf(R.id.songName, R.id.nomArtist)
        )

        liste.adapter = adapt


    }
    override fun onStart() {
        super.onStart()
        k.ajouterObservateur(this)
    }

    override fun onStop() {
        k.retirerObservateur(this)

        super.onStop()
    }

    override fun actualiser(temperature: ArrayList<Chanson>) {
        Toast.makeText(this,"allo",LENGTH_LONG).show()
    }


}