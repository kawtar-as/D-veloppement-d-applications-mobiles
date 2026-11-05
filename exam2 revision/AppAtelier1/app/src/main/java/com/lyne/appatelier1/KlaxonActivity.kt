package com.lyne.appatelier1

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class KlaxonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_klaxon)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //volley permet de se connecter à une serveur externe pour chercher de l'information avec la méthode get
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.ericlabonte.com/articles.json"

        //val StringRequest = StringRequest(Request.Method.GET, url, Repondeur(), RepondeurErreurs())

        //faire la requête sans utiliser des class4es internes, utiliser des expressions lambda a la place
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {reponse -> val li: ListeProduits = Klaxon().parse<ListeProduits>(reponse)?: ListeProduits()},
            {Toast.makeText(this@KlaxonActivity, "Ne fonctionne pas", LENGTH_LONG).show()})

        queue.add(stringRequest)
    }

}