package com.kawtar.tp1

import android.content.Context
import android.content.Intent
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
import java.lang.System.console

class AfficherActiviy : AppCompatActivity() ,Observateur {
    lateinit var liste : ListView
    lateinit var artist :String
    var k = ModeleKlaxon

    var reponse = ArrayList<HashMap<String, Any>>()
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
        }else if (artist == "limpBizkit"){
            url ="https://itunes.apple.com/search?term=limpBizkit&media=music&limit=7"
        }else if (artist == "korn"){
            url ="https://itunes.apple.com/search?term=korn&media=music&limit=7"
        }
//        reponse = decomposerReponse()

        k.chercherChanson(this,url)


    }

    override fun onStart() {
        super.onStart()
        k.ajouterObservateur(this)

    }

    override fun onStop() {
        k.retirerObservateur(this)

        super.onStop()
    }


    override fun actualiser(listes: ListeChansons) {
        reponse.clear()
        Toast.makeText(this,"allo",LENGTH_LONG).show()


        //transformer la liste de chansons en une liste hashmap
        for(l in listes.results){
            val temp = HashMap<String, Any>()
            temp.put("titre", l.trackName) ?: ""
            temp.put("artist",l.artistName)?: ""
            temp.put("url",l.artworkUrl100)?: ""

            reponse.add(temp)
            println("liste" + listes.results.size)

        }
        adapt = SongAdapter(this,
            reponse,
            R.layout.item,
            arrayOf("titre", "artist","url"),
            intArrayOf(R.id.songName, R.id.nomArtist, R.id.cover)
            // cliquer sur une chanson de la liste

        )

        liste.adapter = adapt
        liste.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, reponse.get(position).get("titre").toString(), LENGTH_LONG).show()
            val i = Intent(this@AfficherActiviy, LireActivity::class.java)
            startActivity(i)
          }


    }

    inner class SongAdapter(context: Context, data: List< MutableMap<String, Any>>, resource: Int, from: Array<String>, to: IntArray) : SimpleAdapter(context, data, resource, from, to)  {

        override fun setViewImage(v: ImageView,value:String){
            Glide.with(v.context)
                .load(value)
                .into(v)
        }
    }

}