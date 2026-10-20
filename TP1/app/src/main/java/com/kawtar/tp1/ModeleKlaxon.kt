package com.kawtar.tp1

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

object ModeleKlaxon :Sujet{

    private val observateurs= ArrayList<Observateur>()
    private var listeChanson: ListeChansons = ListeChansons()
    fun chercherChanson(context: Context, artist :String){
        var lien = artist
        val url = "https://itunes.apple.com/search?term=$artist&media=music&limit=7"
        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { reponse ->
                listeChanson = Klaxon().parse<ListeChansons>(reponse) ?: ListeChansons()
                avertirObservateurs()
            },
            { Toast.makeText(context, "Ne fonctionne pas", LENGTH_LONG).show() })

        queue.add(stringRequest)

    }

    override fun ajouterObservateur(o: Observateur) {
        observateurs.add(o)
    }

    override fun retirerObservateur(o: Observateur) {
        observateurs.remove(o)
    }

    override fun avertirObservateurs() {
        for (o in observateurs) {
            o.actualiser(listeChanson)
        }
    }


}
