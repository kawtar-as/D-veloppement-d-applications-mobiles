package com.kawtar.annexe4

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class AccueilActivity : AppCompatActivity() {

    lateinit var text : TextView
    lateinit var connaitre : Button

    lateinit var lanceur: ActivityResultLauncher<Intent>
     var u :Utilisateur? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        text =findViewById(R.id.bonjour)
        connaitre = findViewById(R.id.connaitre)

        // création du lanceur de boomerang, objet sera appelé au retour du boomerang dans cette classe
        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackUtilisateur()
        )

        connaitre.setOnClickListener { lanceur.launch(Intent(this@AccueilActivity,IdentificationActivity::class.java))}

    }
    //si l'utilisateur tourne le téléphone ou change un paramètre
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
//            util = savedInstanceState?.getSerializable("util", Utilisateur::class.java)
//        else
//            util = savedInstanceState?.getSerializable("util") as Utilisateur?
//
//        affichage.text = ("Bonjour ${util?.prenom?:" "} ${util?.nom?:" "} !")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("util", u)
    }

    inner class CallBackUtilisateur: ActivityResultCallback<ActivityResult> {
        override fun onActivityResult(result: ActivityResult) {
            if(result.resultCode == RESULT_OK){
                val intentRetour = result.data  // retourne l'intent
                if(intentRetour != null){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        u = intentRetour.getSerializableExtra("util", Utilisateur::class.java)

                    }else{
                        u = intentRetour.getSerializableExtra("util") as Utilisateur
                    }
                    text.text = ("Bonjour ${u?.prenom} ${u?.nom} !")
                }

            }
            }



    }

}