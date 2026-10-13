package com.kawtar.tp1

import android.app.DownloadManager
import android.app.VoiceInteractor
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class AfficherActiviy : AppCompatActivity() {
    lateinit var liste : ListView
    var artist = ""
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
        try {
            var artist = intent.getStringExtra("artist")
            Toast.makeText(this,"artiste choisi : " + artist.toString(),LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this,"problem!!!!",LENGTH_LONG).show()
        }

        val queue = Volley.newRequestQueue(this)
        var url = ""
        if(artist == "nirvana"){
            url ="https://itunes.apple.com/search?term=nirvana&media=music"
        }else if (artist == "metallica"){
            url ="https://itunes.apple.com/search?term=metallica&media=music"
        }else if (artist == "korn"){
            url ="https://itunes.apple.com/search?term=korn&media=music"
        }

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {response -> val tab = response.getJSONArray("results")
                decomposerReponse(tab)},
            { Toast.makeText(this, "ne marche pas", LENGTH_LONG).show()})
        queue.add(jsonRequest)
    }



    fun decomposerReponse(tab: JSONArray){
        val remplir = ArrayList<HashMap<String, Any>>()
        for( i in 0..tab.length()-1){
            val temp = HashMap<String, Any>()
            temp.put("trackName", tab.getJSONObject(i).getString("trackName"))
            temp.put("artistName", tab.getJSONObject(i).getDouble("artistName"))
            temp.put("previewUrl", tab.getJSONObject(i).getDouble("previewUrl"))

            remplir.add(temp)

        }
        val adapt = SimpleAdapter(
            this,
            remplir,
            R.layout.item,
            arrayOf("trackName", "artistName"),
            intArrayOf(R.id.songName, R.id.nom)
        )

        liste.adapter = adapt

    }
}