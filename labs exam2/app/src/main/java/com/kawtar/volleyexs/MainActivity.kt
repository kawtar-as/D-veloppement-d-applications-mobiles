package com.kawtar.volleyexs

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kawtar.volleyexs.databinding.ActivityMainBinding
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    lateinit var total: TextView
    lateinit var pourcentage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_main)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        var compteur = 0.0
        var p  = 0.0
        total = findViewById(R.id.totsl)
        pourcentage= findViewById(R.id.jsp)


        val queue = Volley.newRequestQueue(this)
        val url = "https://ericlabonte.com/villes.json"
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val villes = JSONArray("villes")
                for(i in 0  until villes.length() ){
                    val population = villes.getDouble(i)
                    compteur += population
                }
                p = compteur /100
                total.text = compteur.toString()
                pourcentage.text = p.toString()
            },
            { error -> Toast.makeText(this,"erreur",LENGTH_LONG).show()}
        )

    }

}