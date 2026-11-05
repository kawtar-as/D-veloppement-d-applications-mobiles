package com.lyne.appatelier1

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //volley permet de se connecter à une serveur externe pour chercher de l'information avec la méthode get
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.ericlabonte.com/articles.json"

        val StringRequest = StringRequest(Request.Method.GET, url, Repondeur(), RepondeurErreurs())

        //faire la requête sans utiliser des class4es internes, utiliser des expressions lambda a la place
        val stringRequest2 = StringRequest(Request.Method.GET, url,
            {reponse -> Toast.makeText(this@MainActivity, reponse.toString(), LENGTH_LONG).show()},
            {Toast.makeText(this@MainActivity, "Ne fonctionne pas", LENGTH_LONG).show()})

        queue.add(StringRequest)
    }

    inner class Repondeur: Response.Listener<String>{
        override fun onResponse(reponse: String?) {
            Toast.makeText(this@MainActivity, reponse.toString(), LENGTH_LONG).show()
        }
    }
    inner class RepondeurErreurs: Response.ErrorListener{
        override fun onErrorResponse(p0: VolleyError?) {
            Toast.makeText(this@MainActivity, "Ne fonctionne pas", LENGTH_LONG).show()

        }

    }
}