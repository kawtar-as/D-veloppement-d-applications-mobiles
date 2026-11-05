package com.lyne.atelier1c

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var champTotal : TextView
    lateinit var champPourcentage : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        champTotal = findViewById(R.id.total)
        champPourcentage = findViewById(R.id.pourcentage)

        val queue = Volley.newRequestQueue(this)
        val url = "https://ericlabonte.com/villes.json"
        val jsonRequest = JsonObjectRequest(Request.Method.GET,
            url,
            null,
            {response ->
                val tab = response.getJSONArray("record")
                val donnees = ArrayList<HashMap<String, Any>>()
                var total = 0
                for(i in 0 until tab.length()){
                    val o = tab.getJSONObject(i)
                    total += o.getInt("population")
                }
                val pct = total * 100.0 / 9067114.0
                champTotal.text = "Total : $total"
                champPourcentage.text = String.format("%.1f %%", pct)

            },
            { volleyError -> println("probleme") }
        )

        queue.add(jsonRequest)
    }
}