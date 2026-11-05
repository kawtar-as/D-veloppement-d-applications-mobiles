package com.lyne.annexe8

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.google.android.material.carousel.KeylineStateList
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    lateinit var champText: TextView
    lateinit var champText2: TextView

    var compteur: Int = 0
    var liste: List<Jeu> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        champText = findViewById(R.id.textView)
        champText2 = findViewById(R.id.textView2)
        val queue = Volley.newRequestQueue(this)

        //partie 1 avec un Volley uniquement
        val url = "https://www.ericlabonte.com/jeux_video.json"

        val jsonRequest = JsonArrayRequest(Request.Method.GET,
            url,
            null,
            {response : JSONArray ->
                val tab = response
                for(i in 0 until tab.length()){
                   if(tab.getJSONObject(i).getString("console") == "PlayStation 5")
                       compteur++
                }
                champText.text = compteur.toString()
            },
            { VolleyError -> println("probleme")}
        )


        //partie 2 avec Klaxon x Volley
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {response: String ->
                liste = Klaxon().parseArray<Jeu>(response)?: emptyList()

                var total = 0.0
                for(jeu in liste){
                    total += jeu.prix       //jeu est un objet donc on peut aller chercher la variable sans get
                }
                var moy = total / liste.size
                champText2.text = moy.toString()
            },
            {VolleyError -> println("probleme")})

        queue.add(jsonRequest)
        queue.add(stringRequest)
    }

}