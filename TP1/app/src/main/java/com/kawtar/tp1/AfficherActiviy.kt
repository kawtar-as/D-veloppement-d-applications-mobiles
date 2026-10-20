package com.kawtar.tp1

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import org.json.JSONArray

class AfficherActiviy : AppCompatActivity() ,Observateur {
    lateinit var liste : ListView
    lateinit var artist :String
    var k = ModeleKlaxon
    lateinit var reponse: ArrayList<HashMap<String, Any>>
    lateinit var adapt: SongAdapter
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
        reponse = decomposerReponse()

        k.chercherChanson(this,artist)


    }
    fun decomposerReponse(): ArrayList<HashMap<String, Any>> {
        val remplir = ArrayList<HashMap<String, Any>>()

        val adapt = SongAdapter(this,
            remplir,
            R.layout.item,
            arrayOf("titre", "artist"),
            intArrayOf(R.id.songName, R.id.nomArtist)
        )

        liste.adapter = adapt
        return remplir

    }
    override fun onStart() {
        super.onStart()
        k.ajouterObservateur(this)
    }

    override fun onStop() {
        k.retirerObservateur(this)

        super.onStop()
    }


    override fun actualiser(liste: ListeChansons) {
        Toast.makeText(this,"allo",LENGTH_LONG).show()
        //transformer la liste de chansons en une liste hashmap
        for(l in liste.listeChansons){
            val temp = HashMap<String, Any>()
            temp.put("titre", l.songName)
            temp.put("songName",l.songName)
            temp.put("url",l.url)

            reponse.add(temp)

        }
    }

    inner class SongAdapter(context: Context, data: List<out MutableMap<String, Any>>, resource: Int, from: Array<String>, to: IntArray) : SimpleAdapter(context, data, resource, from, to)  {

        override fun setViewImage(v: ImageView,value:String){
            Glide.with(v.context)
                .load(value)
                .into(v)
        }
    }

}